package com.example.msexchange.mapper;

import com.example.msexchange.model.repsone.CurrencyApiResponse;
import com.example.msexchange.model.repsone.CurrencyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CurrencyMapperTest {

    CurrencyMapper currencyMapperTest = Mappers.getMapper(CurrencyMapper.class);

    @Test
    public void toCurrencyResponse(){
        var currencyApiResponse = new CurrencyApiResponse();
        currencyApiResponse.setBase_code("USD");
        currencyApiResponse.setDocumentation("jsd");
        currencyApiResponse.setResult("A");
        currencyApiResponse.setConversion_rates(new HashMap<>());
        currencyApiResponse.setTime_last_update_utc("25");
        currencyApiResponse.setTime_next_update_utc("27");
        currencyApiResponse.setTerms_of_use("B");

        var currencyResponse = currencyMapperTest.toCurrencyResponse(currencyApiResponse);

        assertEquals(currencyApiResponse.getBase_code(), currencyResponse.getCurrency());
        assertEquals(currencyApiResponse.getDocumentation(), currencyResponse.getDocumentation());
        assertEquals(currencyApiResponse.getResult(), currencyResponse.getResult());
        assertEquals(currencyApiResponse. getConversion_rates(), currencyResponse.getConversionRates());
        assertEquals(currencyApiResponse.getTime_last_update_utc(), currencyResponse.getTimeLastUpdateUtc());
        assertEquals(currencyApiResponse.getTime_next_update_utc(), currencyResponse.getTimeNextUpdateUtc());
        assertEquals(currencyApiResponse.getTerms_of_use(), currencyResponse.getTermsOfUse());
    }

}
