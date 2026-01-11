package com.example.msexchange.dao.repository;

import com.example.msexchange.dao.entity.PriceAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<PriceAlertEntity, Long> {
    List<PriceAlertEntity> findByExecutedIsFalse();
}
