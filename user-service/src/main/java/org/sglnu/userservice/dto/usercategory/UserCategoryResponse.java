package org.sglnu.userservice.dto.usercategory;

import lombok.Builder;
import org.sglnu.userservice.domain.User;

import java.util.List;

@Builder
public record UserCategoryResponse (User user,Long categoryId)
{
}
