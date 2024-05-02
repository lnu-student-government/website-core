package org.sglnu.categoryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.sglnu.categoryservice.domain.UserCategory;
import org.sglnu.categoryservice.dto.UserCategoryResponse;
import org.sglnu.categoryservice.dto.UserCategoryWithObject;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserCategoryMapper {

    UserCategory toUserCategory(UserCategoryWithObject userCategoryWithObject);
    UserCategoryResponse toUserCategoryResponse(UserCategoryWithObject userCategoryWithObject);
    List<UserCategoryResponse> toUserCategoryResponseList(List<UserCategoryWithObject> userCategoryWithObjects);

}
