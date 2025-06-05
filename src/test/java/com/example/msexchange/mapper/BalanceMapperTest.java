package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.enums.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.example.msexchange.model.enums.Currency.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BalanceMapperTest {
    BalanceMapper balanceMapper = Mappers.getMapper(BalanceMapper.class);

    @Test
    public void createBalanceEntityTest(){
        var user  = new UserEntity();
        user.setId(1L);

        var balanceEntity = balanceMapper.createBalanceEntity(user);

        assertEquals(user, balanceEntity.getUser());
        assertEquals(BigDecimal.valueOf(0,0),balanceEntity.getBalance());
        assertEquals(USD, balanceEntity.getCurrency());

    }
}
