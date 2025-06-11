package com.example.msexchange.schedular;

import com.example.msexchange.mapper.PaymentMapper;
import com.example.msexchange.service.PaymentMessagesOutboxService;
import com.example.msexchange.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PaymentMessageOutboxSchedular {
    PaymentMessagesOutboxService paymentMessagesOutboxService;
    PaymentService paymentService;
    PaymentMapper paymentMapper;


    @Scheduled(cron = "1 * * * * *")
    @SchedulerLock(name = "processOutboxEntries", lockAtLeastFor = "PT1M", lockAtMostFor = "PT3M")
    @Transactional
    public void processOutboxEntries() {
        log.info("processOutboxEntries");
        var unprocessedEntries = paymentMessagesOutboxService.getUnprocessedPaymentMessages();
        unprocessedEntries
                .forEach(entry -> {
                            try {
                                paymentService.sendPaymentEvent(paymentMapper.toUserPaymentPayload(entry));
                                entry.setExecuted(true);
                                paymentMessagesOutboxService.setOutboxExecutedTrue(entry);
                                log.info("Outbox entry successfully processed and marked. ID: {}", entry.getId());
                            } catch (Exception ex) {
                                log.error("Error occurred while processing outbox entry. ID: {} - Will retry.", entry.getId(), ex);
                            }
                        }
                );
    }
}
