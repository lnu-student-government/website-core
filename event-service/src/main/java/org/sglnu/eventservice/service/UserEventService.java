package org.sglnu.eventservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sglnu.eventservice.common.EventRegistrationStatus;
import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.domain.UserEvent;
import org.sglnu.eventservice.dto.*;
import org.sglnu.eventservice.exception.EventNotFoundException;
import org.sglnu.eventservice.exception.UserIsNotSubscribedToEvent;
import org.sglnu.eventservice.mapper.EventMapper;
import org.sglnu.eventservice.repository.EventRepository;
import org.sglnu.eventservice.repository.UserEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.sglnu.eventservice.common.EventRegistrationStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserEventService {

    private final UserEventRepository userEventRepository;

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;


    public EventResponses getUserEventsSubscribedTo(Long userId) {
        List<UserEvent> userEvents = userEventRepository.findByUserId(userId);

        List<Event> events = userEvents.stream()
                .map(UserEvent::getEvent)
                .collect(Collectors.toList());

        return eventMapper.mapToEventResponses(events);
    }

    @Transactional
    public SubscriptionResponse manageSubscription(SubscriptionRequest request) {
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event not found", request.getEventId()));

        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(request.getUserId(), request.getEventId())
                .orElseGet(() -> createUserEvent(request.getUserId(), event));

        if (userEvent.getId() != null) {
            userEvent.setStatus(request.getStatus());
        }

        userEventRepository.save(userEvent);

        return new SubscriptionResponse(request.getEventId(), request.getUserId(), request.getStatus());
    }

    private UserEvent createUserEvent(Long userId, Event event) {
        return UserEvent.builder()
                .userId(userId)
                .event(event)
                .status(getSubscriptionStatus(event))
                .build();
    }

    private EventRegistrationStatus getSubscriptionStatus(Event event) {
        return (event.getIsPaid() || Optional.ofNullable(event.getMaxParticipants()).orElse(0) > 0) ? PENDING : SUBSCRIBED;
    }

    @Transactional
    public SubscriptionResponse rejectParticipant(Long eventId, Long userId) {
        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new UserIsNotSubscribedToEvent("User is not subscribed to this event", userId, eventId));

        userEvent.setStatus(REJECTED);

        userEventRepository.save(userEvent);

        return SubscriptionResponse.builder()
                .eventId(eventId)
                .userId(userId)
                .status(REJECTED)
                .build();
    }
}
