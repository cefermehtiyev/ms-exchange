package com.example.msexchange.model.repsone.coin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoinData {
    private int id;
    private String name;
    private String symbol;
    private String slug;
    private int num_market_pairs;
    private String date_added;
    private List<String> tags;
    private long max_supply;
    private long circulating_supply;
    private long total_supply;
    private boolean infinite_supply;
    private Object platform; // null veya detaylı yapı istersen ayrı class yapabilirsin
    private int cmc_rank;
    private Object self_reported_circulating_supply;
    private Object self_reported_market_cap;
    private Object tvl_ratio;
    private String last_updated;
    private Quote quote;
}
