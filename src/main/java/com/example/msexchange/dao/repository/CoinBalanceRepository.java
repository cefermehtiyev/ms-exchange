package com.example.msexchange.dao.repository;

import com.example.msexchange.dao.entity.CoinBalanceEntity;
import com.example.msexchange.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinBalanceRepository extends JpaRepository<CoinBalanceEntity, Long> {
    Optional<CoinBalanceEntity> findByNameAndUser(String name, UserEntity user);


}
