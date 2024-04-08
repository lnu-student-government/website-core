package com.sglnu.userservice.service;

import com.querydsl.core.types.Predicate;
import com.sglnu.commondatamodel.models.User;

import com.sglnu.commondatamodel.userdto.RegisterRequest;
import com.sglnu.commondatamodel.userdto.UpdateUserRequest;
import com.sglnu.commondatamodel.userdto.UserResponse;
import com.sglnu.userservice.exception.UserNotFoundException;
import com.sglnu.userservice.exception.WrongCredentialsException;
import com.sglnu.userservice.mapper.UserMapper;
import com.sglnu.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


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
        Page<User> users = userRepository.findAll(filters, pageable);
        return users.map(userMapper::mapToUserResponse);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User toUpdateUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new WrongCredentialsException("Phone number is already taken!", request);
        }

        toUpdateUser.setFaculty(request.getFaculty());
        toUpdateUser.setFirstName(request.getFirstName());
        toUpdateUser.setLastName(request.getLastName());
        toUpdateUser.setGroupName(request.getGroupName());
        toUpdateUser.setPhoneNumber(request.getPhoneNumber());
        toUpdateUser.setAvatarId(request.getAvatarId());

        User newUser = userRepository.save(userMapper.updateUser(toUpdateUser, request));
        return userMapper.mapToUserResponse(newUser);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
