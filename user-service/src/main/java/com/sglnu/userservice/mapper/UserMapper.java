package com.sglnu.userservice.mapper;

import com.sglnu.core.domain.models.User;
import com.sglnu.userservice.dto.RegisterRequest;
import com.sglnu.userservice.dto.UpdateUserRequest;
import com.sglnu.userservice.dto.UserRequest;
import com.sglnu.userservice.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User mapToUser(UserRequest request);

    User mapToUser(RegisterRequest request);

    UserResponse mapToUserResponse(User newUser);

    User updateUser(@MappingTarget User user, UpdateUserRequest updateUserRequest);

}
