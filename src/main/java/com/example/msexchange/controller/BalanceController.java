package com.example.msexchange.controller;

import com.example.msexchange.model.enums.Currency;
import com.example.msexchange.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/balances")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BalanceController {
    BalanceService balanceService;

    @PostMapping("/convert")
    public void convertBalanceCurrency(@RequestParam Long id, @RequestParam Currency currency){
        balanceService.convertBalanceCurrency(id, currency);
    }

}
