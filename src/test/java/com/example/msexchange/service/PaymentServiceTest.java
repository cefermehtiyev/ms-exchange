package com.example.msexchange.service;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.PaymentRepository;
import com.example.msexchange.mapper.PaymentMapper;
import com.example.msexchange.model.enums.Currency;
import com.example.msexchange.model.enums.PaymentStatus;
import com.example.msexchange.model.request.BalanceUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.example.msexchange.model.enums.Currency.USD;
import static com.example.msexchange.model.enums.PaymentStatus.SELL_COIN;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private UserService userService;
    @Mock
    private CoinBalanceService coinBalanceService;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private PaymentRepository paymentRepository;

    @Test
    public void recordBalanceChangeTest() {
        var user = new UserEntity();
        var amount = BigDecimal.valueOf(100.0);
        var payment = paymentMapper.toPaymentEntity(amount, user, SELL_COIN, USD);
        paymentService.recordBalanceChange(user, amount, SELL_COIN, USD);
        verify(paymentRepository, times(1)).save(payment);
    }

}
