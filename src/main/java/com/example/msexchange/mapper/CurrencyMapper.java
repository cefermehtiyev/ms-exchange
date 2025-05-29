package com.example.msexchange.mapper;

import com.example.msexchange.model.repsone.CurrencyApiResponse;
import com.example.msexchange.model.repsone.CurrencyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    @Mappings({
            @Mapping(source = "terms_of_use", target = "termsOfUse"),
            @Mapping(source = "time_last_update_utc", target = "timeLastUpdateUtc"),
            @Mapping(source = "time_next_update_utc", target = "timeNextUpdateUtc"),
            @Mapping(source = "base_code", target = "currency"),
            @Mapping(source = "conversion_rates", target = "conversionRates")
    })
    CurrencyResponse toCurrencyResponse(CurrencyApiResponse currencyApiResponse);


}
