package com.example.msexchange.service;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.UserRepository;
import com.example.msexchange.exception.ErrorMessage;
import com.example.msexchange.exception.NotFoundException;
import com.example.msexchange.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.msexchange.exception.ErrorMessage.USER_NOT_FOUND;
import static com.example.msexchange.model.enums.UserRole.ADMIN;
import static com.example.msexchange.model.enums.UserRole.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    AuthService authService;

    @Mock
    UserRepository userRepository;

    @Test
    public void loadUserByUserName_SuccessCase(){
        var userName = "admin";
        var userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setRole(ADMIN);
        userEntity.setPassword("admin123");
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        authService.loadUserByUsername(userName);
        verify(userRepository, times(1)).findByUserName(userName);
    }

    @Test
    public void loadUserByUserName_ErrorCase(){
        var userName = "admin";
        var userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setRole(USER);
        userEntity.setPassword("admin123");
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class,
                () -> authService.loadUserByUsername(userName));
        assertEquals(USER_NOT_FOUND.getMessage(), exception.getMessage());
    }

}
