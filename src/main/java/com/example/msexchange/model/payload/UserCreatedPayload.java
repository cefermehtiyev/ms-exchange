package com.example.msexchange.model.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserCreatedPayload {
    Long userId;
    String role;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
