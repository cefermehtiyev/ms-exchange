package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.enums.Currency;
import com.example.msexchange.model.enums.PaymentStatus;
import com.example.msexchange.model.repsone.coin.USD;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class PaymentMapperTest {

    PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    public void toPaymentEntity() {
        var user = new UserEntity();
        user.setId(1L);
        var amount = BigDecimal.valueOf(100.0);

        var paymentEntity = paymentMapper.toPaymentEntity(amount, user, PaymentStatus.DEPOSIT, Currency.USD);

        assertEquals(user, paymentEntity.getUser());
        assertEquals(Currency.USD, paymentEntity.getCurrency());
        assertEquals(PaymentStatus.DEPOSIT, paymentEntity.getPaymentStatus());
        assertEquals(amount, paymentEntity.getAmount());
    }
}
