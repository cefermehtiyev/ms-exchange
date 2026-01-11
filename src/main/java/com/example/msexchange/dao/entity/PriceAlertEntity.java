package com.example.msexchange.dao.entity;


import com.example.msexchange.model.enums.AlertAction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price_alerts")
@FieldDefaults(level = PRIVATE)
public class PriceAlertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String coinName;
    BigDecimal coinQuantity;
    BigDecimal targetPrice;
    @Enumerated(EnumType.STRING)
    AlertAction action;
    boolean executed;
    @CreationTimestamp
    LocalDateTime createdAt;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE}
    )
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    UserEntity user;
}
