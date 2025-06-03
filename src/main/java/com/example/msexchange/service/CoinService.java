package com.example.msexchange.service;

import com.example.msexchange.client.CoinMarketClient;
import com.example.msexchange.criteria.CoinCriteria;
import com.example.msexchange.criteria.PageCriteria;
import com.example.msexchange.mapper.CoinMapper;
import com.example.msexchange.model.repsone.coin.CoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CoinService {
    @Value("${coin.api.key}")
    private String apiKey;

    private final CoinMarketClient coinMarketClient;
    private final CoinMapper coinMapper;

    public List<CoinResponse> getAllCoin(){
        return coinMarketClient.getLatestListings(apiKey).getData().stream().map(coinMapper::toCoinResponse).toList();
    }

    public CoinResponse getCoin(String name){
        var allCoin = getAllCoin().stream()
                .filter(coin ->name ==  null || coin.getName().equalsIgnoreCase(name)).toList();
        return allCoin.getFirst();
    }
}
