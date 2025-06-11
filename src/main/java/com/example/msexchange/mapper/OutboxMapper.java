package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.PaymentEntity;
import com.example.msexchange.dao.entity.PaymentMessagesOutboxEntity;
import com.example.msexchange.model.payload.UserPaymentPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OutboxMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "executed", constant = "false")
    PaymentMessagesOutboxEntity toPaymentMessagesOutBoxEntity(UserPaymentPayload payload);
}
