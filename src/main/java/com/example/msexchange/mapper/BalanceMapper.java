package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.BalanceEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.request.BalanceUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    @Mapping(target = "balance", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "user", source = "userEntity")
    @Mapping(target = "currency", expression = "java(com.example.msexchange.model.enums.Currency.USD)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BalanceEntity createBalanceEntity(UserEntity userEntity);

    BalanceUpdateDto toBalanceUpdateDto(Long userId, BigDecimal amount);
}
