package com.example.msexchange.service;

import com.example.msexchange.dao.entity.BalanceEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.BalanceRepository;
import com.example.msexchange.exception.ErrorMessage;
import com.example.msexchange.exception.InsufficientBalanceException;
import com.example.msexchange.mapper.BalanceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.msexchange.model.enums.Currency.AZN;
import static com.example.msexchange.model.enums.Currency.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {
    @InjectMocks
    private BalanceService balanceService;
    @Mock
    private BalanceRepository balanceRepository;
    @Mock
    private BalanceMapper balanceMapper;
    @Mock
    private CurrencyService currencyService;
    @Mock
    private PaymentService paymentService;


    @Test
    public void createBalanceTest() {
        var userEntity = new UserEntity();
        userEntity.setId(1L);

        var balanceEntity = balanceMapper.createBalanceEntity(userEntity);
        balanceService.createBalance(userEntity);
        verify(balanceRepository, times(1)).save(balanceEntity);
    }

    @Test
    public void increaseBalance() {
        var userEntity = new UserEntity();
        userEntity.setId(1L);
        var amount = BigDecimal.valueOf(100.0);
        var balanceEntity = new BalanceEntity();
        balanceEntity.setId(1L);
        balanceEntity.setBalance(BigDecimal.valueOf(100.0));
        userEntity.setBalanceEntity(balanceEntity);
        balanceService.increaseBalance(userEntity, amount);
        verify(balanceRepository, times(1)).save(balanceEntity);
    }

    @Test
    public void decreaseBalance_ErrorCase() {
        var userEntity = new UserEntity();
        userEntity.setId(1L);
        var balanceEntity = new BalanceEntity();
        balanceEntity.setId(1L);
        balanceEntity.setBalance(BigDecimal.valueOf(100.0));
        userEntity.setBalanceEntity(balanceEntity);
        var amount = BigDecimal.valueOf(200.0);

        var exception = assertThrows(InsufficientBalanceException.class,
                () -> balanceService.decreaseBalance(userEntity, amount));
        assertEquals(ErrorMessage.INSUFFICIENT_BALANCE_EXCEPTION.getMessage(), exception.getMessage());

    }

    @Test
    public void decreaseBalance_SuccessCase() {
        var userEntity = new UserEntity();
        userEntity.setId(1L);
        var balanceEntity = new BalanceEntity();
        balanceEntity.setId(1L);
        balanceEntity.setBalance(BigDecimal.valueOf(100.0));
        userEntity.setBalanceEntity(balanceEntity);
        var amount = BigDecimal.valueOf(50.0);
        balanceService.decreaseBalance(userEntity, amount);
        verify(balanceRepository, times(1)).save(balanceEntity);
    }

    @Test
    public void convertBalanceCurrency() {
        var balanceEntity = new BalanceEntity();
        balanceEntity.setId(1L);
        balanceEntity.setCurrency(USD);
        balanceEntity.setBalance(BigDecimal.valueOf(100.0));
        var rate = BigDecimal.valueOf(1.7);
        when(currencyService.getExchangeRate(balanceEntity.getCurrency().name(), AZN.name())).thenReturn(rate);
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(balanceEntity));
        balanceService.convertBalanceCurrency(1L, AZN);
        verify(balanceRepository, times(1)).save(balanceEntity);
    }


}
