package org.sglnu.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.sglnu.eventservice.common.EventRegistrationStatus;

@Data
@AllArgsConstructor
@Builder
public class SubscriptionResponse {

    private Long eventId;
    private Long userId;
    private EventRegistrationStatus status;

}
