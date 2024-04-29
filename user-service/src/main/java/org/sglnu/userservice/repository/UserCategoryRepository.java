package org.sglnu.userservice.repository;

import org.sglnu.userservice.domain.User;
import org.sglnu.userservice.domain.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long>,
        QuerydslPredicateExecutor<UserCategory> {


}
