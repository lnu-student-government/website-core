package com.sglnu.userservice.service;

import com.querydsl.core.types.Predicate;
import com.sglnu.userservice.dto.UpdateUserRequest;
import com.sglnu.userservice.dto.UserRequest;
import com.sglnu.userservice.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse save(UserRequest request);

    UserResponse getById(Long id);

    Page<UserResponse> getAll(Pageable pageable, Predicate filters);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

}
