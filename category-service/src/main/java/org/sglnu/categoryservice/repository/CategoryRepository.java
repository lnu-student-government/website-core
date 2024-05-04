package org.sglnu.categoryservice.repository;

import org.sglnu.categoryservice.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {

    boolean existsByName(String name);

    Optional<Category> findByName(String name);

}
