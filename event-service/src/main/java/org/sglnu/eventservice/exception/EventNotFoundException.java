package org.sglnu.eventservice.exception;

import lombok.Getter;

@Getter
public class EventNotFoundException extends RuntimeException {

    private final Long eventId;

    public EventNotFoundException(String message, Long eventId) {
        super(message);
        this.eventId = eventId;
    }

}
