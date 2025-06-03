package com.example.msexchange.model.repsone.coin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Quote {
    @JsonProperty("USD")
    private USD USD;
}