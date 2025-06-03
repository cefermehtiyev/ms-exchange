package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.PaymentEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.enums.PaymentStatus;
import com.example.msexchange.model.request.BalanceUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PaymentEntity toPaymentEntity(BigDecimal amount, UserEntity user, PaymentStatus paymentStatus);
}
