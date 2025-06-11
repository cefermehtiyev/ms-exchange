package com.example.msexchange.mapper;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.payload.UserCreatedPayload;
import com.example.msexchange.model.enums.UserRole;
import com.example.msexchange.model.repsone.UserResponse;
import com.example.msexchange.model.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUserEntity(UserRequest userRequest);
    UserResponse toUserResponse(UserEntity userEntity);
    void updateUser(UserRequest userRequest, @MappingTarget UserEntity userEntity);

    @Mapping(target = "role", source = "role", qualifiedByName = "enumToString")
    @Mapping(target = "userId", source = "id")
    UserCreatedPayload toUserCreatedPayload(UserEntity user);

    @Named("enumToString")
    default String enumToString(UserRole role){
        return role == null ? null : role.name();
    }
}

