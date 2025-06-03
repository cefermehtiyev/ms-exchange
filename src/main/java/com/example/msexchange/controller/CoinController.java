package com.example.msexchange.controller;

import com.example.msexchange.model.repsone.coin.CoinMarketResponse;
import com.example.msexchange.model.repsone.coin.CoinResponse;
import com.example.msexchange.service.CoinService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/coins")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CoinController {

    CoinService coinService;

    @GetMapping
    public List<CoinResponse> getAllCoin() {
        return coinService.getAllCoin();
    }
}
