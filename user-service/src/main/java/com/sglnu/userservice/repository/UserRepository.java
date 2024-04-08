package com.sglnu.userservice.repository;

import com.sglnu.commondatamodel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    boolean existsByPhoneNumber(String username);

    boolean existsByEmailIgnoreCase(String email);

}
