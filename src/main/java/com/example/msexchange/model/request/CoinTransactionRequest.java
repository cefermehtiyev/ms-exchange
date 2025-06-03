package com.example.msexchange.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static com.example.msexchange.model.constants.ValidationConstants.FIELD_CANNOT_BE_NULL;
import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CoinTransactionRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    String coinName;
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    Long userId;
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    BigDecimal coinQuantity;
}
