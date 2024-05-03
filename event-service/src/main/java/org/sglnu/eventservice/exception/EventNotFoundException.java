package org.sglnu.eventservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Getter
public class EventNotFoundException extends RuntimeException {

    private final Long eventId;
    private final String entityName;

    public EventNotFoundException(String message, Long eventId, String entityName) {
        super(message);
        this.eventId = eventId;
        this.entityName = entityName;
    }

}
