package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.PaymentEntity;
import com.example.msexchange.dao.entity.PaymentMessagesOutboxEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.enums.Currency;
import com.example.msexchange.model.enums.PaymentStatus;
import com.example.msexchange.model.payload.UserPaymentPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PaymentEntity toPaymentEntity(BigDecimal amount, UserEntity user, PaymentStatus paymentStatus, Currency currency);
    @Mapping(target = "userId", source = "paymentEntity.user.id")
    @Mapping(target = "email", source = "paymentEntity.user.email")
    UserPaymentPayload toUserPaymentPayload(PaymentEntity paymentEntity);

    UserPaymentPayload toUserPaymentPayload(PaymentMessagesOutboxEntity paymentMessagesOutbox);
}
