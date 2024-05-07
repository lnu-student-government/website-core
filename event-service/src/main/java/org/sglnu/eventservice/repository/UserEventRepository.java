package org.sglnu.eventservice.repository;

import org.sglnu.eventservice.common.EventRegistrationStatus;
import org.sglnu.eventservice.domain.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserEventRepository extends JpaRepository<UserEvent, Long>, QuerydslPredicateExecutor<UserEvent> {

    List<UserEvent> findByUserId(Long userId);

    Optional<UserEvent> findByUserIdAndEventId(Long userId, Long eventId);

    @Query("select count(u) from UserEvent u where u.event.id = :eventId and u.status = :status")
    Long countByEventIdAndStatus(@Param("eventId") Long eventId, @Param("status") EventRegistrationStatus status);

    @Modifying
    @Query("update UserEvent set status = :status where userId = :userId and event.id = :eventId")
    void updateUserEventStatus(@Param("userId") Long userId, @Param("eventId") Long eventId,
                               @Param("status") EventRegistrationStatus status);

}
