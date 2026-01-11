package com.example.msexchange.dao.repository;

import com.example.msexchange.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
