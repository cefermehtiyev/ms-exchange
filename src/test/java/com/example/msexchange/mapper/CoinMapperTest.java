package com.example.msexchange.mapper;

import com.example.msexchange.model.repsone.coin.CoinData;
import com.example.msexchange.model.repsone.coin.Quote;
import com.example.msexchange.model.repsone.coin.USD;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CoinMapperTest {
    CoinMapper coinMapper = Mappers.getMapper(CoinMapper.class);
    @Test
    public void toCoinResponse() {
        var coinData = new CoinData();
        coinData.setName("Bitcoin");
        coinData.setSymbol("BTC");
        coinData.setQuote(new Quote());
        coinData.getQuote().setUSD(new USD());
        coinData.getQuote().getUSD().setPrice(BigDecimal.valueOf(100.0));

        var coinResponse = coinMapper.toCoinResponse(coinData);

        assertEquals(coinData.getName(), coinResponse.getName());
        assertEquals(coinData.getSymbol(), coinResponse.getSymbol());
        assertEquals(coinData.getQuote().getUSD().getPrice(), coinResponse.getPrice());
    }



}
