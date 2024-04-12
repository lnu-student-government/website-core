package org.sglnu.mediaservice.exception;

import lombok.Getter;

@Getter
public class DetailedEntityNotFoundException extends RuntimeException {

    private final Long entityId;
    private final String entityName;

    public DetailedEntityNotFoundException(String message, String entityName, Long entityId) {
        super(message);
        this.entityId = entityId;
        this.entityName = entityName;
    }
}
