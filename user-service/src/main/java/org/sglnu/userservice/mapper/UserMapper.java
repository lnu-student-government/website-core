package org.sglnu.userservice.mapper;

import org.mapstruct.Mapping;
import org.sglnu.userservice.domain.User;
import org.sglnu.userservice.dto.RegisterRequest;
import org.sglnu.userservice.dto.UpdateUserRequest;
import org.sglnu.userservice.dto.UserRequest;
import org.sglnu.userservice.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "faculty", expression = "java(Faculty.forValue(registerRequest.getFaculty()))")
    User map(RegisterRequest registerRequest);

    UserResponse mapToUserResponse(User newUser);

    User updateUser(@MappingTarget User user, UpdateUserRequest updateUserRequest);

}
