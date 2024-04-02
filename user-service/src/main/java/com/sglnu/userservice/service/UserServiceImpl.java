package com.sglnu.userservice.service;

import com.querydsl.core.types.Predicate;
import com.sglnu.core.domain.models.User;
import com.sglnu.userservice.dto.UpdateUserRequest;
import com.sglnu.userservice.dto.UserRequest;
import com.sglnu.userservice.dto.UserResponse;
import com.sglnu.userservice.exception.UserNotFoundException;
import com.sglnu.userservice.exception.WrongCredentialsException;
import com.sglnu.userservice.mapper.UserMapper;
import com.sglnu.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Transactional
    public UserResponse save(UserRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new WrongCredentialsException("Email is already taken!",
                    Map.of("email", "Email is already taken!"));
        }

        User newUser = userRepository.save(userMapper.mapToUser(request));
        return userMapper.mapToUserResponse(newUser);
    }

    public UserResponse getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToUserResponse)
                .orElseThrow(() -> new UserNotFoundException("User with " + id + " not found"));
    }

    public Page<UserResponse> getAll(Pageable pageable, Predicate filters) {
        Page<User> users = userRepository.findAll(filters, pageable);
        return users.map(userMapper::mapToUserResponse);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User newUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new WrongCredentialsException("Email is already taken!",
                    Map.of("email", "Email is already taken!"));
        }

        User savedUser = userRepository.save(userMapper.updateUser(newUser, request));
        return userMapper.mapToUserResponse(savedUser);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
