package com.sglnu.userservice.mapper;

import com.sglnu.commondatamodel.models.User;
import com.sglnu.commondatamodel.userdto.RegisterRequest;
import com.sglnu.commondatamodel.userdto.UpdateUserRequest;
import com.sglnu.commondatamodel.userdto.UserRequest;
import com.sglnu.commondatamodel.userdto.UserResponse;
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
