package org.sglnu.eventservice.exception;

import lombok.Getter;

@Getter
public class UserIsAlreadySubscribed extends RuntimeException {

    private final Long userId;
    private final Long eventId;

    public UserIsAlreadySubscribed(String message, Long userId, Long eventId) {
        super(message);
        this.userId = userId;
        this.eventId = eventId;
    }

}
