package com.example.msexchange.model.request;

import com.example.msexchange.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    String userName;
    String password;
    UserRole role;
}
