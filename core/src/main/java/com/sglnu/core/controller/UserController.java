package com.sglnu.core.controller;

import com.sglnu.core.domain.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        String url = "http://localhost:8080/users";
        return Arrays.asList(restTemplate.getForObject(url, User[].class));
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        String url = "http://localhost:8080/users/" + id;
        return restTemplate.getForObject(url, User.class);
    }

    @PostMapping("/registration")
    public User create() {
        String url = "http://localhost:8080/registration";
        return restTemplate.getForObject(url, User.class);
    }
}