package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.request.PriceAlertRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class PriceAlertMapperTest {

    PriceAlertMapper priceAlertMapper = Mappers.getMapper(PriceAlertMapper.class);
    @Test
    public void toPriceAlertEntity(){
        var userId = 1L;
        var coinName = "Bitcoin";
        var coinQuantity = BigDecimal.valueOf(2.0);
        var priceAlertRequest = new PriceAlertRequest();
        priceAlertRequest.setUserId(userId);
        priceAlertRequest.setCoinName(coinName);
        priceAlertRequest.setCoinQuantity(coinQuantity);
        var user = new UserEntity();
        user.setId(userId);

        var priceAlertEntity = priceAlertMapper.toPriceAlertEntity(priceAlertRequest, user);
        assertEquals(priceAlertRequest.getCoinName(),priceAlertEntity.getCoinName());
        assertEquals(priceAlertRequest.getCoinQuantity(), priceAlertEntity.getCoinQuantity());
        assertEquals(priceAlertRequest.getUserId(), priceAlertEntity.getUser().getId());
    }
}
