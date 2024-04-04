package com.sglnu.categoryservice.service;

import com.querydsl.core.types.Predicate;
import com.sglnu.categoryservice.dto.CategoryRequest;
import com.sglnu.categoryservice.dto.CategoryResponse;
import com.sglnu.categoryservice.dto.UpdateCategoryRequest;
import com.sglnu.categoryservice.exception.CategoryNotFoundException;
import com.sglnu.categoryservice.mapper.CategoryMapper;
import com.sglnu.categoryservice.repository.CategoryRepository;
import com.sglnu.core.domain.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryRequest request){
        if (categoryRepository.existsByName(request.getName())){
            throw new IllegalArgumentException("Category with name " + request.getName() + " already exists");
        }

        Category newCategory = categoryMapper.maptToCategory(request);
        return categoryMapper.mapToCategoryResponse(categoryRepository.save(newCategory));
    }

    public CategoryResponse getById(Long id){
        return categoryRepository.findById(id)
                .map(categoryMapper::mapToCategoryResponse)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
    }

    public Page<CategoryResponse> getAll(Pageable pageable, Predicate predicate){
        return categoryRepository.findAll(predicate, pageable)
                .map(categoryMapper::mapToCategoryResponse);
    }

    public CategoryResponse update(Long id, UpdateCategoryRequest request){
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        category.setName(request.getName());

        return categoryMapper.mapToCategoryResponse(categoryRepository.save(category));
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }


}
