package com.sglnu.categoryservice.controller;

import com.querydsl.core.types.Predicate;
import com.sglnu.categoryservice.dto.CategoryRequest;
import com.sglnu.categoryservice.dto.CategoryResponse;
import com.sglnu.categoryservice.dto.UpdateCategoryRequest;
import com.sglnu.categoryservice.service.CategoryServiceImpl;
import com.sglnu.core.domain.models.User;
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
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;


    @PostMapping("/new-category")
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Error creating user: {}", result.getAllErrors());
            return null;
        }
        return categoryService.create(request);
    }

    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public Page<CategoryResponse> getAll(@PageableDefault Pageable pageable,
                                         @QuerydslPredicate(root = User.class) Predicate filter) {
        return categoryService.getAll(pageable, filter);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        categoryService.delete(id);
    }

}
