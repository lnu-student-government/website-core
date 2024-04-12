package org.sglnu.mediaservice.repository;

import org.sglnu.mediaservice.domain.File;
import org.sglnu.mediaservice.domain.datatype.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long>, QuerydslPredicateExecutor<File> {

    List<File> findAllByTargetTypeAndTargetId(TargetType targetType, Long targetId);
}
