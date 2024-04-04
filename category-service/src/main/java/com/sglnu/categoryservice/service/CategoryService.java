package com.sglnu.categoryservice.service;

import com.querydsl.core.types.Predicate;
import com.sglnu.categoryservice.dto.CategoryRequest;
import com.sglnu.categoryservice.dto.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse getById(Long id);

    Page<CategoryResponse> getAll(Pageable pageable, Predicate predicate);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);

}
