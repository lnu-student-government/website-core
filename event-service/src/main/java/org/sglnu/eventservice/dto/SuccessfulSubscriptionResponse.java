package org.sglnu.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sglnu.eventservice.common.EventRegistrationStatus;

@Data
@AllArgsConstructor
public class SuccessfulSubscriptionResponse {

    private String message;
    private Long eventId;
    private Long userId;
    public EventRegistrationStatus status;

}
