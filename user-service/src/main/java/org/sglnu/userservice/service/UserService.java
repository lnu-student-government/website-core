package org.sglnu.userservice.service;

import com.querydsl.core.types.Predicate;
import org.sglnu.userservice.domain.User;
import org.sglnu.userservice.dto.*;
import org.sglnu.userservice.dto.register.RegisterRequest;
import org.sglnu.userservice.dto.user.UserResponse;
import org.sglnu.userservice.exception.UserNotFoundException;
import org.sglnu.userservice.exception.WrongCredentialsException;
import org.sglnu.userservice.mapper.UserMapper;
import org.sglnu.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Transactional
    public UserResponse save(RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new WrongCredentialsException("Email is already taken!", request);
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
        return userRepository.findAll(filters, pageable).map(userMapper::mapToUserResponse);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User toUpdateUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new WrongCredentialsException("Phone number is already taken!", request);
        }

        User newUser = userRepository.save(userMapper.updateUser(toUpdateUser, request));
        return userMapper.mapToUserResponse(newUser);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(UserNotFoundException::new);
    }

}
