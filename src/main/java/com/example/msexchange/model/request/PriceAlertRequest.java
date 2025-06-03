package com.example.msexchange.model.request;

import com.example.msexchange.model.enums.AlertAction;
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
public class PriceAlertRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    Long userId;
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    String coinName;
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    BigDecimal coinQuantity;
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    BigDecimal targetPrice;
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    AlertAction action;
}
