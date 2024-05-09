package org.sglnu.categoryservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sglnu.categoryservice.common.CategoryMappingStatus;
import org.sglnu.categoryservice.domain.Category;
import org.sglnu.categoryservice.domain.UserCategory;
import org.sglnu.categoryservice.dto.UserCategoryRequest;
import org.sglnu.categoryservice.dto.UserCategoryRequests;
import org.sglnu.categoryservice.dto.UserCategoryResponse;
import org.sglnu.categoryservice.dto.UserCategoryWithObject;
import org.sglnu.categoryservice.exception.CategoryNotFoundException;
import org.sglnu.categoryservice.mapper.UserCategoryMapper;
import org.sglnu.categoryservice.repository.UserCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserCategoryMapper userCategoryMapper;
    private final UserCategoryRepository userCategoryRepository;
    private final CategoryService categoryService;

    @Transactional
    public List<UserCategoryResponse> saveAll(UserCategoryRequests userCategoryRequests) {
        List<UserCategoryWithObject> categories = userCategoryRequests.getUserCategoryRequests().stream()
                .map(this::toUserCategoryWithCategoryAsObject)
                .toList();

        List<UserCategory> toAdd = categories.stream()
                .filter(userCategoryWithObject -> userCategoryWithObject.getStatus().equals(CategoryMappingStatus.SUCCESS))
                .map(userCategoryMapper::toUserCategory)
                .toList();


        userCategoryRepository.saveAll(toAdd);
        return userCategoryMapper.toUserCategoryResponseList(categories);
    }

    private UserCategoryWithObject toUserCategoryWithCategoryAsObject(UserCategoryRequest userCategoryRequest) {
        Category category;
        String categoryName = userCategoryRequest.getCategoryName();

        try {
            category = categoryService.getByName(userCategoryRequest.getCategoryName());
        } catch (CategoryNotFoundException e) {
            return new UserCategoryWithObject(userCategoryRequest.getUserId(), null, categoryName, CategoryMappingStatus.FAILURE);
        }

        return new UserCategoryWithObject(userCategoryRequest.getUserId(), category, categoryName, CategoryMappingStatus.SUCCESS);
    }

}
