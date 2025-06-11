package com.example.msexchange.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void sendMessage(GenericMessage<?> message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(message);

        future.thenAccept(result -> {
            if (Objects.isNull(result)) {
                log.info("Empty result on success for message {}", message);
                return;
            }
            log.info("Message :{} published, topic : {}, partition : {} and offset : {}",
                    message.getPayload(),
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
        }).exceptionally(ex -> {
            log.error("Unable to deliver message to kafka", ex);
            return null;
        });
    }

}
