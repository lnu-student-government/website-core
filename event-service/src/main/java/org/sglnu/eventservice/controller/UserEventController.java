package org.sglnu.eventservice.controller;

import lombok.RequiredArgsConstructor;
import org.sglnu.eventservice.dto.EventResponses;
import org.sglnu.eventservice.dto.SuccessfulSubscriptionResponse;
import org.sglnu.eventservice.dto.SuccessfulUnsubscriptionResponse;
import org.sglnu.eventservice.service.UserEventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("users/{userId}/events")
public class UserEventController {

    private final UserEventService userEventService;

    @GetMapping
    public EventResponses getUserEventsSubscribedTo(@PathVariable Long userId) {
        return userEventService.getUserEventsSubscribedTo(userId);
    }

    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessfulSubscriptionResponse subscribeToEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        return userEventService.subscribeToEvent(userId, eventId);
    }

    @DeleteMapping("/{eventId}")
    public SuccessfulUnsubscriptionResponse unsubscribeFromEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        return userEventService.unsubscribeFromEvent(userId, eventId);
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