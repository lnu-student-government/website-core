package org.sglnu.eventservice.exception;

import lombok.Getter;

@Getter
public class EventIsFullException extends RuntimeException{

    private final Long eventId;
    private final Integer size;

    public EventIsFullException(String message, Long eventId, Integer size) {
        super(message);
        this.eventId = eventId;
        this.size = size;
    }

}
