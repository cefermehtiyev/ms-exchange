package com.example.msexchange.service;

import com.example.msexchange.dao.entity.BalanceEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.BalanceRepository;
import com.example.msexchange.exception.ErrorMessage;
import com.example.msexchange.exception.InsufficientBalanceException;
import com.example.msexchange.exception.NotFoundException;
import com.example.msexchange.mapper.BalanceMapper;
import com.example.msexchange.model.enums.PaymentStatus;
import com.example.msexchange.model.request.BalanceUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.example.msexchange.model.enums.PaymentStatus.DEPOSIT;
import static com.example.msexchange.model.enums.PaymentStatus.WITHDRAW;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BalanceService {

    BalanceRepository balanceRepository;
    BalanceMapper balanceMapper;
    PaymentService paymentService;

    public BalanceService(@Lazy BalanceRepository balanceRepository,
                          @Lazy BalanceMapper balanceMapper,
                          @Lazy PaymentService paymentService) {
        this.balanceRepository = balanceRepository;
        this.balanceMapper = balanceMapper;
        this.paymentService = paymentService;
    }

    public void createBalance(UserEntity userEntity) {
        var balanceEntity = balanceMapper.createBalanceEntity(userEntity);
        balanceRepository.save(balanceEntity);
    }

    public void increaseBalance(UserEntity user, BigDecimal amount) {
        var balance = user.getBalanceEntity();
        balance.setBalance(balance.getBalance().add(amount));
        balanceRepository.save(balance);
        paymentService.recordBalanceChange(user, amount, DEPOSIT);
    }



    public void decreaseBalance(UserEntity user, BigDecimal amount){
        var balance = user.getBalanceEntity();
        deductAmountFromBalance(balance, amount);
        balanceRepository.save(balance);
        paymentService.recordBalanceChange(user, amount, WITHDRAW);
    }

    private void deductAmountFromBalance(BalanceEntity balanceEntity, BigDecimal amount){
        if (balanceEntity.getBalance().compareTo(amount) < 0){
            throw new InsufficientBalanceException(ErrorMessage.INSUFFICIENT_BALANCE_EXCEPTION.getMessage(), 400);
        }
        balanceEntity.setBalance(balanceEntity.getBalance().subtract(amount));
    }

    private BalanceEntity findByUserId(Long id){
        return balanceRepository.findByUserId(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BALANCE_NOT_FOUND.getMessage(), 404)
        );
    }
}
