package org.sglnu.userservice.configuration;


import org.sglnu.userservice.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAwareImpl auditorProvider(){
        return new AuditorAwareImpl();
    }

}
