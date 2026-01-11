package com.example.msexchange.mapper;

import com.example.msexchange.model.repsone.coin.CoinData;
import com.example.msexchange.model.repsone.coin.CoinMarketResponse;
import com.example.msexchange.model.repsone.coin.CoinResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CoinMapper {

    @Mapping(target = "price", source = "quote.USD.price")
    CoinResponse toCoinResponse(CoinData coinData);
}
