package com.example.msexchange.service;

import com.example.msexchange.dao.entity.CoinBalanceEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.CoinBalanceRepository;
import com.example.msexchange.exception.ErrorMessage;
import com.example.msexchange.exception.NotFoundException;
import com.example.msexchange.mapper.CoinBalanceMapper;
import com.example.msexchange.model.request.CoinTransactionRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.example.msexchange.model.enums.PaymentStatus.BUY_COIN;
import static com.example.msexchange.model.enums.PaymentStatus.SELL_COIN;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CoinBalanceService {
    CoinService coinService;
    CoinBalanceRepository coinBalanceRepository;
    CoinBalanceMapper coinBalanceMapper;
    UserService userService;
    BalanceService balanceService;
    PaymentService paymentService;

    public CoinBalanceService(@Lazy CoinService coinService,
                              @Lazy CoinBalanceRepository coinBalanceRepository,
                              @Lazy CoinBalanceMapper coinBalanceMapper,
                              @Lazy UserService userService,
                              @Lazy BalanceService balanceService,
                              @Lazy PaymentService paymentService) {
        this.coinService = coinService;
        this.coinBalanceRepository = coinBalanceRepository;
        this.coinBalanceMapper = coinBalanceMapper;
        this.userService = userService;
        this.balanceService = balanceService;
        this.paymentService = paymentService;
    }

    public void topUpCoinBalance(CoinTransactionRequest transactionRequest) {
        var totalPrice = calculateTotalPrice(transactionRequest.getCoinName(), transactionRequest.getCoinQuantity());
        var user = userService.getUserEntity(transactionRequest.getUserId());
        var coinBalance = coinBalanceRepository.findByNameAndUser(transactionRequest.getCoinName(), user)
                .map(coinBalanceEntity -> {
                            increaseCoinQuantity(coinBalanceEntity, transactionRequest.getCoinQuantity());
                            return coinBalanceEntity;
                        }
                )
                .orElseGet(() -> coinBalanceMapper.toCoinBalanceEntity(transactionRequest.getCoinName(), transactionRequest.getCoinQuantity(), user));

        balanceService.decreaseBalance(user, totalPrice);
        coinBalanceRepository.save(coinBalance);
        paymentService.recordBalanceChange(user, totalPrice, BUY_COIN);
    }

    public void decreaseCoinBalance(CoinTransactionRequest transactionRequest) {
        var user = userService.getUserEntity(transactionRequest.getUserId());
        var coinBalance = findCoinBalanceEntity(transactionRequest.getCoinName(), user);
        deductFromCoinBalance(coinBalance, transactionRequest.getCoinQuantity());
        var totalPrice = calculateTotalPrice(transactionRequest.getCoinName(), transactionRequest.getCoinQuantity());
        balanceService.increaseBalance(user, totalPrice);
        coinBalanceRepository.save(coinBalance);
        paymentService.recordBalanceChange(user, totalPrice, SELL_COIN);
    }

    private void deductFromCoinBalance(CoinBalanceEntity coinBalanceEntity, BigDecimal deductQuantity) {
        coinBalanceEntity.setCoinQuantity(coinBalanceEntity.getCoinQuantity().subtract(deductQuantity));
    }

    private BigDecimal calculateTotalPrice(String coinName, BigDecimal coinQuantity) {
        var coin = coinService.getCoin(coinName);
        return coin.getPrice().multiply(coinQuantity);
    }

    private void increaseCoinQuantity(CoinBalanceEntity coinBalanceEntity, BigDecimal IncreaseQuantity) {
        coinBalanceEntity.setCoinQuantity(coinBalanceEntity.getCoinQuantity().add(IncreaseQuantity));
    }


    private CoinBalanceEntity findCoinBalanceEntity(String name, UserEntity user) {
        return coinBalanceRepository.findByNameAndUser(name, user)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.COIN_BALANCE_NOT_FOUND.getMessage(), 404)
                );
    }
}

