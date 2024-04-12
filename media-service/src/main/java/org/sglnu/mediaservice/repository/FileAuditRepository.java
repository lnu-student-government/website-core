package org.sglnu.mediaservice.repository;

import org.sglnu.mediaservice.domain.FileAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileAuditRepository extends JpaRepository<FileAudit, Long> {
}
