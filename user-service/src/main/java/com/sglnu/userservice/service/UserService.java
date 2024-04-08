package com.sglnu.userservice.service;

import com.querydsl.core.types.Predicate;
import com.sglnu.commondatamodel.userdto.UpdateUserRequest;
import com.sglnu.commondatamodel.userdto.UserRequest;
import com.sglnu.commondatamodel.userdto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse save(UserRequest request);

    UserResponse getById(Long id);

    Page<UserResponse> getAll(Pageable pageable, Predicate filters);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

}
