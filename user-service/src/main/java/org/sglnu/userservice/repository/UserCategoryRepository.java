package org.sglnu.userservice.repository;

import org.sglnu.userservice.dto.usercategory.UserCategory;
import org.sglnu.userservice.dto.usercategory.UserCategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {

    UserCategory save(UserCategoryResponse userCategoryResponse);
}
