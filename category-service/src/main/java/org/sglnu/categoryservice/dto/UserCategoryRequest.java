package org.sglnu.categoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCategoryRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String categoryName;

}
