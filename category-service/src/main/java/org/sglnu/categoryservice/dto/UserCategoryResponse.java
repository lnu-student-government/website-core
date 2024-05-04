package org.sglnu.categoryservice.dto;

import lombok.Data;
import org.sglnu.categoryservice.common.CategoryMappingStatus;

@Data
public class UserCategoryResponse {

    private Long userId;
    private String categoryName;
    private CategoryMappingStatus status;

}
