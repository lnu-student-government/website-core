package org.sglnu.userservice.dto.usercategory;

import lombok.Builder;
import org.sglnu.userservice.domain.User;

@Builder
public record UserCategoryResponse (Long user,Long categoryId)
{
}
