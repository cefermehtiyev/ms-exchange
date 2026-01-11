package com.example.msexchange.dao.repository;

import com.example.msexchange.dao.entity.BalanceEntity;
import com.example.msexchange.dao.entity.PaymentEntity;
import com.example.msexchange.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<BalanceEntity,Long> {
    Long user(UserEntity user);

    Optional<BalanceEntity> findByUserId(Long id);
}
