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
import com.example.msexchange.model.request.CoinTransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PaymentService {
    BalanceService balanceService;
    UserService userService;
    CoinBalanceService coinBalanceService;
    PaymentMapper paymentMapper;
    PaymentRepository paymentRepository;
    KafkaProducer kafkaProducer;
    UserPaymentTopicProperties userPaymentTopicProperties;
    PaymentMessagesOutboxService paymentMessagesOutboxService;

    public void recordBalanceChange(UserEntity user, BigDecimal amount, PaymentStatus paymentStatus,Currency currency){
        var paymentEntity = paymentMapper.toPaymentEntity(amount, user, paymentStatus, currency);
        paymentRepository.save(paymentEntity);
        var payload =paymentMapper.toUserPaymentPayload(paymentEntity);
        try {
          sendPaymentEvent(payload);

        }catch (Exception ex){
            log.info("payload save outbox");
            paymentMessagesOutboxService.saveOutbox(payload);
        }

    }

    public void sendPaymentEvent(UserPaymentPayload payload){
        Map<String, Object> headers = new HashMap<>();
        headers.put(TOPIC, userPaymentTopicProperties.getTopicName());
        headers.put(KafkaHeaders.KEY, payload.getUserId().toString());
        kafkaProducer.sendMessage(new GenericMessage<>(payload, headers));
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
