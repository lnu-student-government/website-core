package org.sglnu.userservice.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.domain.User;
import org.sglnu.userservice.dto.AuthenticationRequest;
import org.sglnu.userservice.dto.AuthenticationResponse;
import org.sglnu.userservice.dto.RegisterRequest;
import org.sglnu.userservice.repository.UserRepository;
import org.sglnu.userservice.security.UsersDetails;
import org.sglnu.userservice.security.auth.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        //TODO: Use mapper instead please
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFaculty(registerRequest.getFaculty());
        user.setGroupName(registerRequest.getGroupName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        //

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(new UsersDetails(user));

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getPhoneNumber());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Wrong username or password!");
        }

        String jwt = jwtService.generateToken(userDetails);
        injectCookieToTheResponse(response, jwt);
        return new AuthenticationResponse(jwt);
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
