package com.example.msexchange.model.request;

import com.example.msexchange.model.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.msexchange.model.constants.ValidationConstants.FIELD_CANNOT_BE_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    String userName;
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    String password;
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    UserRole role;
}
