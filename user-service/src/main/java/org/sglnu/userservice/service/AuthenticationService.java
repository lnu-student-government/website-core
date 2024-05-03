package org.sglnu.userservice.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.client.dto.UserCategoryResponse;
import org.sglnu.userservice.domain.User;
import org.sglnu.userservice.dto.AuthenticationRequest;
import org.sglnu.userservice.dto.AuthenticationResponse;
import org.sglnu.userservice.dto.RegisterRequest;
import org.sglnu.userservice.dto.TokenResponse;
import org.sglnu.userservice.exception.EmailAlreadyUsedException;
import org.sglnu.userservice.exception.PasswordMismatchException;
import org.sglnu.userservice.exception.PhoneNumberAlreadyUsedException;
import org.sglnu.userservice.mapper.UserMapper;
import org.sglnu.userservice.repository.UserRepository;
import org.sglnu.userservice.security.UsersDetails;
import org.sglnu.userservice.security.auth.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserMapper userMaper;
    private final UserCategoryService userCategoryService;
    private final UserService userService;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatedPassword())) {
            throw new PasswordMismatchException("Passwords don't match!");
        }
        if(userService.findUserByEmail(registerRequest.getEmail()) != null){
            throw new EmailAlreadyUsedException("Email already used");
        }
        if(userService.findUserByPhoneNumber(registerRequest.getPhoneNumber()) != null){
            throw new PhoneNumberAlreadyUsedException("Phone number already used");
        }
        User user = userMaper.map(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);
        List<UserCategoryResponse> categoryResponse = userCategoryService.assignCategoriesToUser(savedUser.getId(), registerRequest.getCategories());

        UsersDetails userDetails = new UsersDetails(user);
        var jwtToken = jwtService.generateToken(userDetails);
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + (1000 * 60 * 60 * 24);
        TokenResponse tokenResponse = new TokenResponse(jwtToken, currentTime, expirationTime);
        return new AuthenticationResponse(userMaper.mapToUserResponse(savedUser), categoryResponse, tokenResponse);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getPhoneNumber());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new PasswordMismatchException("Incorrect password or email");
        }

        User user = userService.findByPhoneNumber(request.getPhoneNumber());

        String jwt = jwtService.generateToken(userDetails);
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + (1000 * 60 * 60 * 24);
        injectCookieToTheResponse(response, jwt);
        TokenResponse tokenResponse = new TokenResponse(jwt, currentTime, expirationTime);
        return new AuthenticationResponse(userMaper.mapToUserResponse(user), null, tokenResponse);
    }

    private void injectCookieToTheResponse(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie("access-token", jwt);
        cookie.setMaxAge(900);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
    }

}
