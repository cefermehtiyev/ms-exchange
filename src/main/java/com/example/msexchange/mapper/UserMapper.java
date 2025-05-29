package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.repsone.UserResponse;
import com.example.msexchange.model.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUserEntity(UserRequest userRequest,String alma);
    UserResponse toUserResponse(UserEntity userEntity);
    void updateUser(UserRequest userRequest, @MappingTarget UserEntity userEntity);
}
