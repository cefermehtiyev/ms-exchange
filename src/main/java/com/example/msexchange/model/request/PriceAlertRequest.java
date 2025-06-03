package com.example.msexchange.model.request;

import com.example.msexchange.model.enums.AlertAction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PriceAlertRequest {
    Long userId;
    String coinName;
    BigDecimal coinQuantity;
    BigDecimal targetPrice;
    AlertAction action;
}
