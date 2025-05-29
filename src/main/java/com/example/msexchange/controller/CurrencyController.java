package com.example.msexchange.controller;

import com.example.msexchange.model.repsone.CurrencyResponse;
import com.example.msexchange.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exchange")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CurrencyController {
    CurrencyService currencyService;

    @GetMapping
    public CurrencyResponse getAllExchange(@RequestParam String currency){
        return currencyService.getAllExchange(currency);
    }

}
