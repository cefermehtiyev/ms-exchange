package com.example.msexchange.service;

import com.example.msexchange.model.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.msexchange.model.enums.UserRole.ADMIN;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SuperAdminInitializerTest {
    @InjectMocks
    SuperAdminInitializer superAdminInitializer;

    @Mock
    UserService userService;
    @Test
    public void initialize_NewSuperAdmin()throws Exception{
        var userRequest = new UserRequest();
        userRequest.setUserName("admin");
        userRequest.setPassword("admin123");
        userRequest.setEmail("admin@gmail.com");
        userRequest.setRole(ADMIN);
        when(userService.existsByUserName(userRequest.getUserName())).thenReturn(false);
        superAdminInitializer.run("SuperAdminInitializer");
        verify(userService, times(1)).registerUser(userRequest);
    }

    
    @Test
    public void AlreadyExists_SuperAdmin()throws Exception{
        var userRequest = new UserRequest();
        userRequest.setUserName("admin");
        userRequest.setPassword("admin123");
        userRequest.setRole(ADMIN);
        when(userService.existsByUserName(userRequest.getUserName())).thenReturn(true);
        superAdminInitializer.run("SuperAdminInitializer");
        verify(userService, times(0)).registerUser(userRequest);

    }
}
