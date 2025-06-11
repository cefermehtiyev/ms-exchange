package com.example.msexchange.service;

import com.example.msexchange.dao.entity.PaymentEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.PaymentRepository;
import com.example.msexchange.kafka.producer.KafkaProducer;
import com.example.msexchange.kafka.properties.UserPaymentTopicProperties;
import com.example.msexchange.mapper.PaymentMapper;
import com.example.msexchange.model.enums.Currency;
import com.example.msexchange.model.enums.PaymentStatus;
import com.example.msexchange.model.payload.UserPaymentPayload;
import com.example.msexchange.model.request.BalanceUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.example.msexchange.model.enums.Currency.USD;
import static com.example.msexchange.model.enums.PaymentStatus.SELL_COIN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

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
    @Mock
    private UserPaymentTopicProperties userPaymentTopicProperties;
    @Mock
    private KafkaProducer kafkaProducer;
    @Mock
    private PaymentMessagesOutboxService paymentMessagesOutboxService;


    @Test
    public void recordBalanceChangeTest() {
        //given
        var user = new UserEntity();
        user.setId(1L);
        var amount = BigDecimal.valueOf(100.0);
        var payment = new PaymentEntity();
        var payload = new UserPaymentPayload();
        payload.setUserId(1L);

        //when
        when(paymentMapper.toPaymentEntity(amount, user, SELL_COIN, USD)).thenReturn(payment);
        when(paymentMapper.toUserPaymentPayload(payment)).thenReturn(payload);

        //then
        paymentService.recordBalanceChange(user, amount, SELL_COIN, USD);
        verify(paymentRepository, times(1)).save(payment);
        verify(kafkaProducer, times(1)).sendMessage(any());
        verify(paymentMessagesOutboxService, times(0)).saveOutbox(payload);
    }

}




