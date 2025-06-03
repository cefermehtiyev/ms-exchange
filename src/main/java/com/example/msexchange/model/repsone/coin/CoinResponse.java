package com.example.msexchange.model.repsone.coin;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CoinResponse {
    String name;
    String symbol;
    BigDecimal price;
}
