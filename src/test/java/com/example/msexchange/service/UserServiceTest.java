package com.example.msexchange.service;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.UserRepository;
import com.example.msexchange.exception.AlreadyExistException;
import com.example.msexchange.exception.ErrorMessage;
import com.example.msexchange.exception.NotFoundException;
import com.example.msexchange.kafka.producer.KafkaProducer;
import com.example.msexchange.kafka.properties.UserCreatedTopicProperties;
import com.example.msexchange.mapper.UserMapper;
import com.example.msexchange.model.enums.UserRole;
import com.example.msexchange.model.payload.UserCreatedPayload;
import com.example.msexchange.model.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.msexchange.exception.ErrorMessage.USER_ALREADY_EXCEPTION;
import static com.example.msexchange.exception.ErrorMessage.USER_NOT_FOUND;
import static com.example.msexchange.model.enums.UserRole.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    BalanceService balanceService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserCreatedTopicProperties userCreatedTopicProperties;
    @Mock
    KafkaProducer kafkaProducer;

    @Test
    public void registerUser_SuccessCase() {
        var userRequest = new UserRequest();
        userRequest.setUserName("user");
        userRequest.setPassword("user123");
        userRequest.setRole(USER);
        var userEntity = new UserEntity();
        userEntity.setId(1L);
        var userPayload = new UserCreatedPayload();
        Map<String, Object> headers = new HashMap<>();
        headers.put(TOPIC, userCreatedTopicProperties.getTopicName());
        headers.put(KafkaHeaders.KEY, userEntity.getId().toString());
        when(userMapper.toUserEntity(userRequest)).thenReturn(userEntity);
        when(userMapper.toUserCreatedPayload(userEntity)).thenReturn(userPayload);
        when(userRepository.existsByUserName(userRequest.getUserName())).thenReturn(false);
        userService.registerUser(userRequest);
        verify(userRepository, times(1)).save(userEntity);
        verify(kafkaProducer, times(1)).sendMessage(any());
    }

    @Test
    public void registerUser_ErrorCase(){
        var userRequest = new UserRequest();
        userRequest.setUserName("user");
        when(userRepository.existsByUserName(userRequest.getUserName())).thenReturn(true);
        var exception = assertThrows(AlreadyExistException.class,
                () -> userService.registerUser(userRequest));
        assertEquals(USER_ALREADY_EXCEPTION.getMessage(), exception.getMessage());

    }
}
