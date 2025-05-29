package com.example.msexchange.client;

import com.example.msexchange.decoder.CustomErrorDecoder;
import com.example.msexchange.model.repsone.CurrencyApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchangeRateClient", url = "https://v6.exchangerate-api.com/v6k",configuration = CustomErrorDecoder.class)
public interface CurrencyClient {

    @GetMapping("/{apiKey}/latest/{base}")
    CurrencyApiResponse getExchangeRates(
            @PathVariable("apiKey") String apiKey,
            @PathVariable("base") String baseCurrency
    );
}
