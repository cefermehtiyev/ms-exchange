package com.example.msexchange.dao.entity;


import com.example.msexchange.model.enums.Currency;
import com.example.msexchange.model.enums.PaymentStatus;
import com.example.msexchange.service.PaymentService;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@FieldDefaults(level = PRIVATE)
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    BigDecimal amount;

    @Enumerated(STRING)
    Currency currency;

    @Enumerated(STRING)
    PaymentStatus paymentStatus;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE}
    )
    @JoinColumn(name = "user_id")
    UserEntity user;


}
