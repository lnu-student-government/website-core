package org.sglnu.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sglnu.eventservice.common.EventRegistrationStatus;

@Data
@AllArgsConstructor
public class SubscriptionRequest {

    private Long eventId;
    private Long userId;
    private EventRegistrationStatus status;

}
