package com.sglnu.core.domain.config;

import com.sglnu.core.domain.config.auditableuser.AuditableConfig;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareIml implements AuditorAware<AuditableConfig> {

    private Optional<AuditableConfig> auditableConfig = Optional.empty();

    public void setAuditableUser(AuditableConfig auditableUser){
        this.auditableConfig = Optional.of(auditableUser);
    }

    @Override
    public Optional<AuditableConfig> getCurrentAuditor() {
        return Optional.empty();
    }
}
