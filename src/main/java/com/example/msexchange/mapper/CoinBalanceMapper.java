package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.CoinBalanceEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.request.CoinPaymentDetail;
import com.example.msexchange.model.request.CoinPurchaseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CoinBalanceMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    CoinBalanceEntity toCoinBalanceEntity(String name, BigDecimal coinQuantity, UserEntity user);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    CoinBalanceEntity createBalanceEntity(UserEntity user);
}
