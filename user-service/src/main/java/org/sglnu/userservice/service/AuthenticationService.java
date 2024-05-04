package org.sglnu.userservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.client.dto.UserCategoryResponse;
import org.sglnu.userservice.domain.User;
import org.sglnu.userservice.dto.AuthenticationRequest;
import org.sglnu.userservice.dto.AuthenticationResponse;
import org.sglnu.userservice.dto.RegisterRequest;
import org.sglnu.userservice.dto.TokenResponse;
import org.sglnu.userservice.exception.FieldAlreadyUsedException;
import org.sglnu.userservice.exception.FieldAlreadyUsedExceptions;
import org.sglnu.userservice.exception.UserNotFoundException;
import org.sglnu.userservice.mapper.UserMapper;
import org.sglnu.userservice.security.UsersDetails;
import org.sglnu.userservice.security.auth.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserCategoryService userCategoryService;
    private final UserService userService;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        checkIfUserAlreadyExists(registerRequest);

        User savedUser = userService.save(registerRequest);
        List<UserCategoryResponse> categoryResponse = userCategoryService.assignCategoriesToUser(savedUser.getId(), registerRequest.getCategories());

        TokenResponse tokenResponse = getTokenResponse(savedUser);
        return new AuthenticationResponse(userMapper.mapToUserResponse(savedUser), categoryResponse, tokenResponse);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userService.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new FieldAlreadyUsedException("password", "Phone number or password is incorrect!");
        }

        TokenResponse tokenResponse = getTokenResponse(user);
        return new AuthenticationResponse(userMapper.mapToUserResponse(user), null, tokenResponse);
    }

    private void checkIfUserAlreadyExists(RegisterRequest registerRequest) {
        List<FieldAlreadyUsedException> validationErrors = new ArrayList<>();

        userService.findUserByEmail(registerRequest.getEmail())
                .ifPresent(user -> validationErrors.add(new FieldAlreadyUsedException("email", "Email already used")));

        userService.findByPhoneNumber(registerRequest.getPhoneNumber())
                .ifPresent(user -> validationErrors.add(new FieldAlreadyUsedException("phoneNumber", "Phone number already used")));

        if (!validationErrors.isEmpty()) {
            throw new FieldAlreadyUsedExceptions(validationErrors);
        }
    }

    private TokenResponse getTokenResponse(User user) {
        UsersDetails userDetails = new UsersDetails(user);
        var jwtToken = jwtService.generateToken(userDetails);
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + (1000 * 60 * 60 * 24);
        return new TokenResponse(jwtToken, currentTime, expirationTime);
    }

}
