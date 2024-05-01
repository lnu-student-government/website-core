package org.sglnu.categoryservice.service;

import com.querydsl.core.types.Predicate;
import org.sglnu.categoryservice.domain.Category;
import org.sglnu.categoryservice.dto.CategoryRequest;
import org.sglnu.categoryservice.dto.CategoryResponse;
import org.sglnu.categoryservice.dto.UpdateCategoryRequest;
import org.sglnu.categoryservice.exception.CategoryNotFoundException;
import org.sglnu.categoryservice.mapper.CategoryMapper;
import org.sglnu.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryRequest request){
        if (categoryRepository.existsByName(request.getName())){
            throw new IllegalArgumentException("Category with name " + request.getName() + " already exists");
        }

        Category newCategory = categoryMapper.mapToCategory(request);
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

    public Category getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException("Category with name " + name + " not found"));
    }

    public CategoryResponse update(Long id, UpdateCategoryRequest request){
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        Category updatedCategory = categoryMapper.mapToCategory(request, category);

        return categoryMapper.mapToCategoryResponse(categoryRepository.save(updatedCategory));
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

}
