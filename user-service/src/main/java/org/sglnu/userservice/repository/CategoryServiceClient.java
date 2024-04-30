package org.sglnu.userservice.repository;

import org.sglnu.userservice.dto.usercategory.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.function.Predicate;

@FeignClient(name = "category-service")
public interface CategoryServiceClient {

    @GetMapping("/categories/{id}")
    CategoryResponse getById(Long id);
    @GetMapping
    Page<CategoryResponse> getAll(Pageable pageable, Predicate predicate);

}
