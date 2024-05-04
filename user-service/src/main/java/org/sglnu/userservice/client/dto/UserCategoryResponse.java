package org.sglnu.userservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCategoryResponse {

    private Long userId;
    private String categoryName;
    private String status;

}
