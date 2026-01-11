package com.example.msexchange.service;

import com.example.msexchange.dao.entity.BalanceEntity;
import com.example.msexchange.dao.entity.CoinBalanceEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.CoinBalanceRepository;
import com.example.msexchange.exception.NotFoundException;
import com.example.msexchange.mapper.CoinBalanceMapper;
import com.example.msexchange.model.repsone.coin.CoinResponse;
import com.example.msexchange.model.request.CoinTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.msexchange.exception.ErrorMessage.COIN_BALANCE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoinBalanceServiceTest {
    @InjectMocks
    private CoinBalanceService coinBalanceService;
    @Mock
    private CoinService coinService;
    @Mock
    private CoinBalanceRepository coinBalanceRepository;
    @Mock
    private CoinBalanceMapper coinBalanceMapper;
    @Mock
    private UserService userService;
    @Mock
    private BalanceService balanceService;
    @Mock
    private PaymentService paymentService;


    @Test
    public void topUpCoinBalance_NewBalance() {
        //
        var coinTransactionRequest = new CoinTransactionRequest();
        coinTransactionRequest.setCoinName("Bitcoin");
        coinTransactionRequest.setCoinQuantity(BigDecimal.valueOf(1));
        coinTransactionRequest.setUserId(1L);
        var userEntity = new UserEntity();
        userEntity.setId(1L);
        var coinResponse = new CoinResponse();
        coinResponse.setPrice(BigDecimal.valueOf(105000));
        var coinBalance = coinBalanceMapper.toCoinBalanceEntity(coinTransactionRequest.getCoinName(), coinTransactionRequest.getCoinQuantity(), userEntity);
        var balanceEntity = new BalanceEntity();
        userEntity.setCoinBalanceEntity(coinBalance);
        userEntity.setBalanceEntity(balanceEntity);

        //when
        when(userService.getUserEntity(1L)).thenReturn(userEntity);
        when(coinService.getCoin(coinTransactionRequest.getCoinName())).thenReturn(coinResponse);
        when(coinBalanceRepository.findByNameAndUser(coinTransactionRequest.getCoinName(), userEntity)).thenReturn(Optional.empty());

        // then
        coinBalanceService.topUpCoinBalance(coinTransactionRequest);
        verify(coinBalanceRepository, times(1)).save(coinBalance);
    }

    @Test
    public void topUpCoinBalance_ExistingBalance() {
        var coinTransactionRequest = new CoinTransactionRequest();
        coinTransactionRequest.setCoinName("Bitcoin");
        coinTransactionRequest.setCoinQuantity(BigDecimal.valueOf(1));
        coinTransactionRequest.setUserId(1L);
        var userEntity = new UserEntity();
        userEntity.setId(1L);
        var coinResponse = new CoinResponse();
        coinResponse.setPrice(BigDecimal.valueOf(105000));
        var coinBalance = new CoinBalanceEntity();
        coinBalance.setUser(userEntity);
        var balanceEntity = new BalanceEntity();
        coinBalance.setCoinQuantity(BigDecimal.valueOf(0.0));
        userEntity.setBalanceEntity(balanceEntity);

        when(userService.getUserEntity(userEntity.getId())).thenReturn(userEntity);
        when(coinService.getCoin(coinTransactionRequest.getCoinName())).thenReturn(coinResponse);
        when(coinBalanceRepository.findByNameAndUser(coinTransactionRequest.getCoinName(), userEntity)).thenReturn(Optional.of(coinBalance));
        coinBalanceService.topUpCoinBalance(coinTransactionRequest);
        verify(coinBalanceRepository, times(1)).save(coinBalance);
    }

    @Test
    public void decreaseCoinBalance_SuccessCase() {
        var coinTransactionRequest = new CoinTransactionRequest();
        coinTransactionRequest.setCoinName("Bitcoin");
        coinTransactionRequest.setCoinQuantity(BigDecimal.valueOf(1));
        coinTransactionRequest.setUserId(1L);
        var user = new UserEntity();
        user.setId(1L);
        var coinBalance = new CoinBalanceEntity();
        coinBalance.setName("Bitcoin");
        coinBalance.setCoinQuantity(BigDecimal.valueOf(100.00));
        user.setCoinBalanceEntity(coinBalance);
        var coinResponse = new CoinResponse();
        coinResponse.setPrice(BigDecimal.valueOf(105000));
        var balance = new BalanceEntity();
        user.setBalanceEntity(balance);

        when(coinService.getCoin(coinTransactionRequest.getCoinName())).thenReturn(coinResponse);
        when(userService.getUserEntity(user.getId())).thenReturn(user);
        when(coinBalanceRepository.findByNameAndUser(coinTransactionRequest.getCoinName(), user)).thenReturn(Optional.of(coinBalance));
        coinBalanceService.decreaseCoinBalance(coinTransactionRequest);
        verify(coinBalanceRepository, times(1)).save(coinBalance);
    }

    @Test
    public void decreaseCoinBalance_ErrorCase() {
        var coinTransactionRequest = new CoinTransactionRequest();
        coinTransactionRequest.setCoinName("Bitcoin");
        coinTransactionRequest.setCoinQuantity(BigDecimal.valueOf(1));
        coinTransactionRequest.setUserId(1L);
        var user = new UserEntity();
        user.setId(1L);
        when(userService.getUserEntity(user.getId())).thenReturn(user);
        when(coinBalanceRepository.findByNameAndUser(coinTransactionRequest.getCoinName(), user)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class,
                () -> coinBalanceService.decreaseCoinBalance(coinTransactionRequest));
        assertEquals(COIN_BALANCE_NOT_FOUND.getMessage(), exception.getMessage());
        verify(coinBalanceRepository, times(0)).save(any());
    }



}
