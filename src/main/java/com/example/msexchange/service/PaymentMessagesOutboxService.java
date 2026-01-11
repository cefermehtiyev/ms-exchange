package com.example.msexchange.service;

import com.example.msexchange.dao.entity.PaymentMessagesOutboxEntity;
import com.example.msexchange.dao.repository.PaymentMessagesOutboxRepository;
import com.example.msexchange.mapper.OutboxMapper;
import com.example.msexchange.model.payload.UserPaymentPayload;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PaymentMessagesOutboxService {
    PaymentMessagesOutboxRepository paymentMessagesOutboxRepository;
    OutboxMapper outboxMapper;

    public void saveOutbox(UserPaymentPayload payload){
        paymentMessagesOutboxRepository.save(outboxMapper.toPaymentMessagesOutBoxEntity(payload));
    }

    public List<PaymentMessagesOutboxEntity> getUnprocessedPaymentMessages(){
        return paymentMessagesOutboxRepository.findByExecutedIsFalse();
    }

    public void setOutboxExecutedTrue(PaymentMessagesOutboxEntity paymentMessagesOutbox){
        paymentMessagesOutbox.setExecuted(true);
        paymentMessagesOutboxRepository.save(paymentMessagesOutbox);
    }

}
