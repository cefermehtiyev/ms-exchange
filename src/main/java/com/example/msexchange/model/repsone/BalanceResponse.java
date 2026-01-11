package com.example.msexchange.model.repsone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class BalanceResponse {
    Long userId;
    BigDecimal balance;
}
