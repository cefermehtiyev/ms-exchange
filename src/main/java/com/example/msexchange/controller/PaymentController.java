package com.example.msexchange.controller;

import com.example.msexchange.model.request.BalanceUpdateDto;

import com.example.msexchange.model.request.CoinPurchaseRequest;
import com.example.msexchange.model.request.CoinSellRequest;
import com.example.msexchange.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @PostMapping("/top-up")
    public void topUpBalance(@RequestBody BalanceUpdateDto BalanceUpdateDto){
        paymentService.topUpBalance(BalanceUpdateDto);
    }

    @PostMapping("/purchase-coin")
    public void purchaseCoin(@RequestBody CoinPurchaseRequest coinPurchaseRequest){
        paymentService.purchaseCoin(coinPurchaseRequest);
    }

    @PostMapping("/sell-coin")
    public void sellCoin(@RequestBody CoinSellRequest coinSellRequest){
        paymentService.sellCoin(coinSellRequest);
    }

    @GetMapping
    public BigDecimal getTotalPrice(@RequestBody CoinPurchaseRequest coinPurchaseRequest){
        return paymentService.calculateTotalPrice(coinPurchaseRequest);
    }





}
