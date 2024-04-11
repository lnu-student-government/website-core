package org.sglnu.userservice.audit;


import org.sglnu.userservice.domain.User;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    //don`t work 11.04
    @Override
    public Optional<String> getCurrentAuditor() {
        Neo4jProperties.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of(authentication.getName()); // Повертаємо ім'я користувача
    }
}
