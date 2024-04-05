package com.sglnu.userservice.controller;

import com.querydsl.core.types.Predicate;
import com.sglnu.core.domain.models.User;
import com.sglnu.userservice.dto.RegisterRequest;
import com.sglnu.userservice.dto.UpdateUserRequest;
import com.sglnu.userservice.dto.UserResponse;
import com.sglnu.userservice.mapper.UserMapper;
import com.sglnu.userservice.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

        private final UserServiceImpl userService;


        @PostMapping("/registration")
        public UserResponse create(@Valid @RequestBody RegisterRequest request, BindingResult result) {
            if (result.hasErrors()) {
                log.error("Error creating user: {}", result.getAllErrors());
                return null;
            }
            return userService.save(request);
        }

        @GetMapping("/{id}")
        public UserResponse getById(@PathVariable Long id) {
            return userService.getById(id);
        }

        @GetMapping
        public Page<UserResponse> getAll(@PageableDefault Pageable pageable,
                                         @QuerydslPredicate(root = User.class) Predicate filter) {
            return userService.getAll(pageable, filter);
        }

        @PutMapping("/{id}")
        public UserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
            return userService.update(id, request);
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteById(@PathVariable Long id) {
            userService.delete(id);
        }
}
