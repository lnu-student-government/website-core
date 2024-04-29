package org.sglnu.userservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.dto.authentication.AuthenticationRequest;
import org.sglnu.userservice.dto.authentication.AuthenticationResponse;
import org.sglnu.userservice.dto.register.RegisterRequest;
import org.sglnu.userservice.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody RegisterRequest registerRequest, @Valid@RequestBody List<Long> categoriesId){
        return authenticationService.register(registerRequest,categoriesId);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse register(@Valid @RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response){
        return authenticationService.authenticate(authenticationRequest, response);
    }
}
