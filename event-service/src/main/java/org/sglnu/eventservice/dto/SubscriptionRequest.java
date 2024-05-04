package org.sglnu.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionRequest {

    private Long eventId;
    private Long userId;
    private String action;

}
