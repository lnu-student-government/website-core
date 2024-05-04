package org.sglnu.userservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserCategoryRequests {
    private List<UserCategoryRequest> userCategoryRequests;

}
