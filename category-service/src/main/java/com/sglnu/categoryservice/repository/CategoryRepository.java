package com.sglnu.categoryservice.repository;

import com.sglnu.core.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {

    boolean existsByName(String name);

}
