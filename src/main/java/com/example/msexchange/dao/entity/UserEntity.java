package com.example.msexchange.dao.entity;

import com.example.msexchange.model.enums.UserRole;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.*;
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
@Table(name = "users")
@FieldDefaults(level = PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String userName;
    String password;
    String email;
    @Enumerated(STRING)
    UserRole role;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToOne(
            cascade = {PERSIST, MERGE, REMOVE},
            mappedBy = "user"
    )
    BalanceEntity balanceEntity;

    @OneToOne(
            cascade = {PERSIST, MERGE, REMOVE},
            mappedBy = "user"
    )
    CoinBalanceEntity coinBalanceEntity;

    @OneToMany(
            cascade = {PERSIST, MERGE, REMOVE},
            mappedBy = "user"
    )
    List<PriceAlertEntity> priceAlertEntity;



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
