package org.sglnu.userservice.security.auth;


import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.domain.User;
import org.sglnu.userservice.dto.RegisterRequest;
import org.sglnu.userservice.repository.UserRepository;
import org.sglnu.userservice.security.JwtService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFaculty(registerRequest.getFaculty());
        user.setGroupName(registerRequest.getGroupName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        userRepository.save(user);
        var jwtToken = jwtService.generateToken((UserDetails) user);

        return new AuthenticationResponse().builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhoneNumber(),authenticationRequest.getPassword()));

        var user = userRepository.searchUserByPhoneNumber(authenticationRequest.getPhoneNumber()).orElseThrow();
        var jwtToken = jwtService.generateToken((UserDetails) user);

        return new AuthenticationResponse().builder().token(jwtToken).build();
    }
}
