package com.example.msexchange.dao.entity;

import com.example.msexchange.model.enums.Currency;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balances")
@FieldDefaults(level = PRIVATE)
public class BalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigDecimal balance;
    @Enumerated(STRING)
    Currency currency;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToOne(
            fetch = LAZY,
            cascade = {PERSIST, MERGE}
    )
    @JoinColumn(name = "user_id")
    UserEntity user;

}
