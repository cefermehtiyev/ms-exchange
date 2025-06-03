package com.example.msexchange.service;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.PaymentRepository;
import com.example.msexchange.mapper.PaymentMapper;
import com.example.msexchange.model.enums.Currency;
import com.example.msexchange.model.enums.PaymentStatus;
import com.example.msexchange.model.request.BalanceUpdateDto;
import com.example.msexchange.model.request.CoinTransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PaymentService {
    BalanceService balanceService;
    UserService userService;
    CoinBalanceService coinBalanceService;
    PaymentMapper paymentMapper;
    PaymentRepository paymentRepository;

    public void recordBalanceChange(UserEntity user, BigDecimal amount, PaymentStatus paymentStatus,Currency currency){
        var paymentEntity = paymentMapper.toPaymentEntity(amount, user, paymentStatus, currency);
        paymentRepository.save(paymentEntity);
    }

    @Transactional
    public void topUpBalance(BalanceUpdateDto balanceUpdateDto) {
        var user = userService.getUserEntity(balanceUpdateDto.getUserId());
        balanceService.increaseBalance(user, balanceUpdateDto.getAmount());
    }
    @Transactional
    public void purchaseCoin(CoinTransactionRequest coinTransactionRequest){
        coinBalanceService.topUpCoinBalance(coinTransactionRequest);
    }
    @Transactional
    public void sellCoin(CoinTransactionRequest coinTransactionRequest){
        coinBalanceService.decreaseCoinBalance(coinTransactionRequest);
    }


    public BigDecimal calculateTotalPrice(CoinTransactionRequest coinTransactionRequest){
        return null;
    }
}
