package com.example.msexchange.model.repsone.coin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoinMarketResponse {
//    private Status status;
    private List<CoinData> data;
}