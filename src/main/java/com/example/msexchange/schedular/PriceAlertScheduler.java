package com.example.msexchange.schedular;

import com.example.msexchange.dao.repository.PriceAlertRepository;
import com.example.msexchange.mapper.BalanceMapper;
import com.example.msexchange.mapper.CoinBalanceMapper;
import com.example.msexchange.service.CoinService;
import com.example.msexchange.service.PaymentService;
import com.example.msexchange.service.PriceAlertService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.msexchange.model.enums.AlertAction.BUY;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PriceAlertScheduler {
    PriceAlertService priceAlertService;
    PaymentService paymentService;
    CoinBalanceMapper coinBalanceMapper;
    CoinService coinService;
    PriceAlertRepository priceAlertRepository;

    @Scheduled(cron = "* 1 * * * *")
    @SchedulerLock(name = "checkPriceAlerts", lockAtLeastFor = "PT1M", lockAtMostFor = "PT3M")
    @Transactional
    public void checkPriceAlerts(){
        priceAlertService.getAllUnProcessedAlerts().forEach(
                alert -> {
                    var coin = coinService.getCoin(alert.getCoinName());
                    if (coin.getPrice().compareTo(alert.getCoinQuantity()) >= 0){
                        var transactionRequest = coinBalanceMapper.toTransactionRequest(alert.getCoinName(), alert.getUser().getId(), alert.getCoinQuantity());
                        if (alert.getAction().equals(BUY)){
                            log.info("Executing BUY alert for userId={}, coin={}, quantity={}",
                                    alert.getUser().getId(), alert.getCoinName(), alert.getCoinQuantity());
                            paymentService.purchaseCoin(transactionRequest);
                        }else{
                            log.info("Executing SELL alert for userId={}, coin={}, quantity={}",
                                    alert.getUser().getId(), alert.getCoinName(), alert.getCoinQuantity());
                            paymentService.sellCoin(transactionRequest);
                        }
                        alert.setExecuted(true);
                        priceAlertRepository.save(alert);
                    }
                }
        );
    }
}
