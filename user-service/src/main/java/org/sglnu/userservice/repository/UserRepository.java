package org.sglnu.userservice.repository;

import org.sglnu.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}
