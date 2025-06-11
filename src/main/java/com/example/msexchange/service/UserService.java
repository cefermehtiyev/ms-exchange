package com.example.msexchange.service;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.UserRepository;
import com.example.msexchange.exception.AlreadyExistException;
import com.example.msexchange.exception.ErrorMessage;
import com.example.msexchange.exception.NotFoundException;
import com.example.msexchange.kafka.producer.KafkaProducer;
import com.example.msexchange.kafka.properties.UserCreatedTopicProperties;
import com.example.msexchange.mapper.UserMapper;
import com.example.msexchange.model.payload.UserCreatedPayload;
import com.example.msexchange.model.repsone.UserResponse;
import com.example.msexchange.model.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.msexchange.exception.ErrorMessage.USER_ALREADY_EXCEPTION;
import static lombok.AccessLevel.PRIVATE;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    BalanceService balanceService;


    KafkaProducer kafkaProducer;
    UserCreatedTopicProperties userCreatedTopicProperties;

    @Transactional
    public void registerUser(UserRequest userRequest) {
        ifUserExistThrowException(userRequest.getUserName());
        userRequest.setPassword(buildPasswordEncoder(userRequest.getPassword()));
        var userEntity = userMapper.toUserEntity(userRequest);
        userRepository.save(userEntity);
        balanceService.createBalance(userEntity);
        UserCreatedPayload payload = userMapper.toUserCreatedPayload(userEntity);
        log.info("userId {}:",payload.getUserId());
        Map<String, Object> headers = new HashMap<>();
        headers.put(TOPIC, userCreatedTopicProperties.getTopicName());
        headers.put(KafkaHeaders.KEY, userEntity.getId().toString());

        kafkaProducer.sendMessage(new GenericMessage<>(payload, headers));
    }

    public void updateUser(Long id, UserRequest userRequest) {
        var userEntity = findById(id);
        if (!userEntity.getUserName().equals(userRequest.getUserName())) {
            ifUserExistThrowException(userRequest.getUserName());
        }
        userRequest.setPassword(buildPasswordEncoder(userRequest.getPassword()));
        userMapper.updateUser(userRequest, userEntity);
        userRepository.save(userEntity);
    }

    private void ifUserExistThrowException(String userName) {
        if (existsByUserName(userName)) {
            throw new AlreadyExistException(USER_ALREADY_EXCEPTION.getMessage(), 409);
        }
    }

    public UserEntity getUserEntity(Long id) {
        return findById(id);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    private String buildPasswordEncoder(String password) {
        return passwordEncoder.encode(password);
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage(), 404)
                );
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

}

