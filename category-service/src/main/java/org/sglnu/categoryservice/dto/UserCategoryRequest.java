package org.sglnu.categoryservice.dto;

import lombok.Data;

@Data
public class UserCategoryRequest {

    private Long userId;
    private String categoryName;

}
