package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CoinBalanceMapperTest {
    CoinBalanceMapper coinBalanceMapper = Mappers.getMapper(CoinBalanceMapper.class);

    @Test
    public void toCoinBalanceEntityTest() {
        var name = "Bitcoin";
        var coinQuantity = BigDecimal.valueOf(1.3);
        var user = new UserEntity();
        user.setId(1L);

        var coinBalanceEntity = coinBalanceMapper.toCoinBalanceEntity(name, coinQuantity, user);

        assertEquals(name, coinBalanceEntity.getName());
        assertEquals(coinQuantity, coinBalanceEntity.getCoinQuantity());
        assertEquals(user, coinBalanceEntity.getUser());
    }

    @Test
    public void toTransactionRequest() {
        var coinName = "Bitcoin";
        var userId = 1L;
        var coinQuantity = BigDecimal.valueOf(1);

        var coinTransactionsRequest = coinBalanceMapper.toTransactionRequest(coinName, userId, coinQuantity);

        assertEquals(coinName, coinTransactionsRequest.getCoinName());
        assertEquals(userId, coinTransactionsRequest.getUserId());
        assertEquals(coinQuantity, coinTransactionsRequest.getCoinQuantity());

    }
}
