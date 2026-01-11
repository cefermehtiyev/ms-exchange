package com.example.msexchange.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_outbox_messages")
@FieldDefaults(level = PRIVATE)
public class PaymentMessagesOutboxEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    Long userId;
    String email;
    String paymentStatus;
    BigDecimal amount;
    LocalDateTime createdAt;
    boolean executed;
}
