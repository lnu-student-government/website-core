package com.sglnu.core.domain.config.auditableuser;

import com.sglnu.core.domain.config.AuditorAwareIml;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
public class AuditableConfig {

    @Bean
    AuditorAwareIml auditorAwareIml(){
        return new AuditorAwareIml();
    }
}
