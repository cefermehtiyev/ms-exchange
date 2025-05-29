package com.example.msexchange.service;

import com.example.msexchange.client.CurrencyClient;
import com.example.msexchange.mapper.CurrencyMapper;
import com.example.msexchange.model.repsone.CurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    @Value("${exchange.api.key}")
    private String apiKey;

    private final CurrencyMapper currencyMapper;
    private final CurrencyClient currencyClient;

    public CurrencyResponse getAllExchange(String baseCurrency) {
        var currencyRateApiResponse = currencyClient.getExchangeRates(apiKey, baseCurrency);
        return currencyMapper.toCurrencyResponse(currencyRateApiResponse);

    }

}