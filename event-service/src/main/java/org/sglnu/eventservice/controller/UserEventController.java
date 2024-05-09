package org.sglnu.eventservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.sglnu.eventservice.common.EventRegistrationStatus;
import org.sglnu.eventservice.dto.*;
import org.sglnu.eventservice.service.UserEventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("events/{eventId}/users/{userId}")
public class UserEventController {

    private final UserEventService userEventService;

    @GetMapping
    public EventResponses getUserEventsSubscribedTo(@PathVariable Long userId) {
        return userEventService.getUserEventsSubscribedTo(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Valid
    public SubscriptionResponse manageSubscription(@RequestBody SubscriptionRequest request) {
        return userEventService.manageSubscription(request);
    }

}
