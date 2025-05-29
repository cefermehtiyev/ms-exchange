package com.example.msexchange.model.repsone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {
    private String result;
    private String documentation;
    private String termsOfUse;
    private String timeLastUpdateUtc;
    private String timeNextUpdateUtc;
    private String currency;
    private Map<String, Double> conversionRates;
}
