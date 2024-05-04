package org.sglnu.userservice.client;

import org.sglnu.userservice.client.dto.UserCategoryRequests;
import org.sglnu.userservice.client.dto.UserCategoryResponse;
import org.sglnu.userservice.configuration.feign.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "category-service", configuration = FeignClientConfig.class)
public interface CategoryServiceClient {

    @PutMapping("/categories/users")
    List<UserCategoryResponse> assignCategoriesToUser(UserCategoryRequests userCategoryRequests);

}
