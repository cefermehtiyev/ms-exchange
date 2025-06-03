package com.example.msexchange.schedular;

import com.example.msexchange.mapper.BalanceMapper;
import com.example.msexchange.model.enums.AlertAction;
import com.example.msexchange.model.request.CoinPurchaseRequest;
import com.example.msexchange.service.CoinService;
import com.example.msexchange.service.PaymentService;
import com.example.msexchange.service.PriceAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.msexchange.model.enums.AlertAction.BUY;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceAlertScheduler {
    PriceAlertService priceAlertService;
    PaymentService paymentService;
    BalanceMapper balanceMapper;

//    @Scheduled(cron = "1 * * * * *")
//    @SchedulerLock(name = "processOutboxEntries", lockAtLeastFor = "PT1M", lockAtMostFor = "PT3M")
//    @Transactional
//    public void checkPriceAlerts(){
//        priceAlertService.getAllUnProcessedAlerts().stream().map(
//                alert -> {
//                    if (alert.getAction().equals(BUY)){
//                        paymentService.purchaseCoin(balanceMapper.toBalanceUpdateDto(alert.getUser().getId(), alert.));
//                    }
//                }
//        );
//    }
}
