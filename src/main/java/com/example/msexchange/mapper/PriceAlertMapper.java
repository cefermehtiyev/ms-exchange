package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.PriceAlertEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.request.PriceAlertRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceAlertMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "executed", constant = "false")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    PriceAlertEntity toPriceAlertEntity(PriceAlertRequest priceAlertRequest, UserEntity user);
}
