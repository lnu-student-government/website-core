package org.sglnu.categoryservice.mapper;

import org.mapstruct.MappingTarget;
import org.sglnu.categoryservice.domain.Category;
import org.sglnu.categoryservice.dto.CategoryRequest;
import org.sglnu.categoryservice.dto.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.sglnu.categoryservice.dto.UpdateCategoryRequest;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category mapToCategory(CategoryRequest request);

    CategoryResponse mapToCategoryResponse(Category category);

    Category mapToCategory(UpdateCategoryRequest request, @MappingTarget Category category);
}
