package org.sglnu.eventservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sglnu.eventservice.common.EventRegistrationStatus;
import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.domain.UserEvent;
import org.sglnu.eventservice.dto.*;
import org.sglnu.eventservice.mapper.EventMapper;
import org.sglnu.eventservice.repository.UserEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.sglnu.eventservice.common.EventRegistrationStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserEventService {

    private final UserEventRepository userEventRepository;

    private final EventMapper eventMapper;


    public EventResponses getUserEventsSubscribedTo(Long userId) {
        List<UserEvent> userEvents = userEventRepository.findByUserId(userId);

        List<Event> events = userEvents.stream()
                .map(UserEvent::getEvent)
                .collect(Collectors.toList());

        return eventMapper.mapToEventResponses(events);
    }

    @Transactional
    public SubscriptionResponse manageSubscription(Long userId, Long eventId, EventRegistrationStatus action) {
        userEventRepository.updateUserEventStatus(userId, eventId, action);
        return new SubscriptionResponse("User with id=[%s] %s event with id=[%s]"
                .formatted(userId, action.name().toLowerCase(), eventId), eventId, userId, action);
    }

}
