package com.example.msexchange.service;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.UserRepository;
import com.example.msexchange.exception.AlreadyExistException;
import com.example.msexchange.exception.ErrorMessage;
import com.example.msexchange.exception.NotFoundException;
import com.example.msexchange.mapper.UserMapper;
import com.example.msexchange.model.repsone.UserResponse;
import com.example.msexchange.model.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.msexchange.exception.ErrorMessage.USER_ALREADY_EXCEPTION;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService  {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public void registerUser(UserRequest userRequest){
        ifUserExistThrowException(userRequest.getUserName());
        userRequest.setPassword(buildPasswordEncoder(userRequest.getPassword()));
        userRepository.save(userMapper.toUserEntity(userRequest,""));
    }

    public void updateUser(Long id, UserRequest userRequest){
        var userEntity = findById(id);
        if(!userEntity.getUserName().equals(userRequest.getUserName())){
            ifUserExistThrowException(userRequest.getUserName());
        }
        userRequest.setPassword(buildPasswordEncoder(userRequest.getPassword()));
        userMapper.updateUser(userRequest, userEntity);
        userRepository.save(userEntity);
    }

    private void ifUserExistThrowException(String userName){
        if (existsByUserName(userName)){
            throw new AlreadyExistException(USER_ALREADY_EXCEPTION.getMessage(), 409);
        }
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    private String buildPasswordEncoder(String password) {
        return passwordEncoder.encode(password);
    }

    private UserEntity findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage(), 404)
                );
    }

    public boolean existsByUserName(String userName){
        return userRepository.existsByUserName(userName);
    }
    
}

