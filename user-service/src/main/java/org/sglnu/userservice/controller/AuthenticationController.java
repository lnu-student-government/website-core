package org.sglnu.userservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.dto.AuthenticationRequest;
import org.sglnu.userservice.dto.AuthenticationResponse;
import org.sglnu.userservice.dto.RegisterRequest;
import org.sglnu.userservice.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest registerRequest){
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse register(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response){
        return authenticationService.authenticate(authenticationRequest, response);
    }
}
