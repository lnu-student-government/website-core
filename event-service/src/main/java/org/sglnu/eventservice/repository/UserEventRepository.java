package org.sglnu.eventservice.repository;

import org.sglnu.eventservice.domain.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface UserEventRepository extends JpaRepository<UserEvent, Long>, QuerydslPredicateExecutor<UserEvent> {

    List<UserEvent> findByUserId(Long userId);

    Optional<UserEvent> findByUserIdAndEventId(Long userId, Long eventId);

}
