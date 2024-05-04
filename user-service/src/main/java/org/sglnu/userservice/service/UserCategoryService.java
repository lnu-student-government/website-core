package org.sglnu.userservice.service;

import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.client.CategoryServiceClient;
import org.sglnu.userservice.client.dto.UserCategoryRequest;
import org.sglnu.userservice.client.dto.UserCategoryRequests;
import org.sglnu.userservice.client.dto.UserCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final CategoryServiceClient client;

    public List<UserCategoryResponse> assignCategoriesToUser(Long id, List<String> categories) {
        UserCategoryRequests userCategoryRequests = new UserCategoryRequests(
                categories.stream()
                        .map(categoryName -> new UserCategoryRequest(id, categoryName))
                        .toList()
        );

        return client.assignCategoriesToUser(userCategoryRequests);
    }

}
