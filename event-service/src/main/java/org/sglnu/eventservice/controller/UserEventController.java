package org.sglnu.eventservice.controller;

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
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionResponse manageSubscription(@RequestBody SubscriptionRequest request) throws InvalidInputException {
        if ("subscribe".equals(request.getAction())) {
            userEventService.subscribeToEvent(request.getUserId(), request.getEventId());
            return new SubscriptionResponse("User subscribed to event", request.getEventId(), request.getUserId(),
                    EventRegistrationStatus.APPROVED);
        } else if ("unsubscribe".equals(request.getAction())) {
            userEventService.unsubscribeFromEvent(request.getUserId(), request.getEventId());
            return new SubscriptionResponse("User unsubscribed from event", request.getEventId(), request.getUserId(),
                    EventRegistrationStatus.UNSUBSCRIBED);
        } else {
            throw new InvalidInputException("Invalid action");
        }
    }

    @PutMapping("/{eventId}/approve")
    public void approveUser(@PathVariable Long userId, @PathVariable Long eventId) {
        userEventService.approveUser(userId, eventId);
    }

    @PutMapping("/{eventId}/reject")
    public void rejectUser(@PathVariable Long userId, @PathVariable Long eventId) {
        userEventService.rejectUser(userId, eventId);
    }
}