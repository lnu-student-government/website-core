package com.sglnu.core.domain.config.auditableuser;


import org.springframework.data.repository.CrudRepository;

public interface AuditableUserRepository extends CrudRepository<AuditableConfig,Long> {
}
