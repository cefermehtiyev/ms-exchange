package com.example.msexchange.dao.repository;

import com.example.msexchange.dao.entity.PaymentMessagesOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMessagesOutboxRepository extends JpaRepository<PaymentMessagesOutboxEntity, Long> {
    List<PaymentMessagesOutboxEntity> findByExecutedIsFalse();
}
