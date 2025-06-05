package com.example.msexchange.mapper;

import com.example.msexchange.model.repsone.coin.Quote;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CoinMapperTest {
    CoinMapper coinMapper = Mappers.getMapper(CoinMapper.class);

    public void toCoinResponse() {

    }

}
