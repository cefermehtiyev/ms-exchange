package com.example.msexchange.service;

import com.example.msexchange.model.enums.UserRole;
import com.example.msexchange.model.request.UserRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SuperAdminInitializer implements CommandLineRunner {
    UserService userService;


    @Override
    public void run(String... args) throws Exception {
        if (!userService.existsByUserName("admin")) {
            userService.registerUser(new UserRequest("admin", "admin123", UserRole.ADMIN));
        }
    }


}
