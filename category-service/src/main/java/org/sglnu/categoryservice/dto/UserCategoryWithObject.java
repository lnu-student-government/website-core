package org.sglnu.categoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sglnu.categoryservice.common.CategoryMappingStatus;
import org.sglnu.categoryservice.domain.Category;

@Data
@AllArgsConstructor
public class UserCategoryWithObject {

    private Long userId;
    private Category category;
    private String categoryName;
    private CategoryMappingStatus status;

}
