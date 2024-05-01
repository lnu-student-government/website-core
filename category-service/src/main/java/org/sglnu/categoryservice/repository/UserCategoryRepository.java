package org.sglnu.categoryservice.repository;

import org.sglnu.categoryservice.domain.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

}
