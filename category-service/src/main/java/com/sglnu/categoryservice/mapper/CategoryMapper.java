package com.sglnu.categoryservice.mapper;

import com.sglnu.categoryservice.dto.CategoryRequest;
import com.sglnu.categoryservice.dto.CategoryResponse;
import com.sglnu.core.domain.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category maptToCategory(CategoryRequest request);

    CategoryResponse mapToCategoryResponse(Category category);

}
