package com.example.msexchange.client;

import com.example.msexchange.model.repsone.coin.CoinMarketResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coinMarketCapClient", url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency")
public interface CoinMarketClient {

    @GetMapping("/listings/latest")
    CoinMarketResponse getLatestListings(
        @RequestHeader("X-CMC_PRO_API_KEY") String apiKey
    );
}
