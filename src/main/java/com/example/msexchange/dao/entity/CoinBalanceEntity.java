package com.example.msexchange.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coin_balances")
@FieldDefaults(level = PRIVATE)
public class CoinBalanceEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String name;
    BigDecimal coinQuantity;

    @OneToOne(
            fetch = LAZY,
            cascade = {PERSIST, MERGE}
    )
    @JoinColumn(name = "user_id")
    UserEntity user;

}
