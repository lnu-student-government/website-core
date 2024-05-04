package org.sglnu.categoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sglnu.categoryservice.dto.UserCategoryRequests;
import org.sglnu.categoryservice.dto.UserCategoryResponse;
import org.sglnu.categoryservice.service.UserCategoryService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class UserCategoryController {

    private final UserCategoryService userCategoryService;

    @PutMapping("/users")
    public List<UserCategoryResponse> saveAll(@Valid @RequestBody UserCategoryRequests userCategoryRequests) {
        return userCategoryService.saveAll(userCategoryRequests);
    }

}
