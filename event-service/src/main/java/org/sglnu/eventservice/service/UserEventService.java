package org.sglnu.eventservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sglnu.eventservice.common.EventRegistrationStatus;
import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.domain.UserEvent;
import org.sglnu.eventservice.dto.*;
import org.sglnu.eventservice.exception.UserIsNotSubscribedToEvent;
import org.sglnu.eventservice.mapper.EventMapper;
import org.sglnu.eventservice.repository.UserEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.sglnu.eventservice.common.EventRegistrationStatus.*;
import static reactor.core.publisher.SignalType.SUBSCRIBE;

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
    public SubscriptionResponse manageSubscription(SubscriptionRequest request) {
        userEventRepository.findByUserIdAndEventId(request.getUserId(), request.getEventId())
                        .orElseThrow(() -> new UserIsNotSubscribedToEvent("User with id=[%s] is not subscribed to event with id=[%s]"
                                .formatted(request.getUserId(), request.getEventId()), request.getUserId(), request.getEventId()));

        userEventRepository.updateUserEventStatus(request.getUserId(), request.getEventId(), request.getStatus());

        return new SubscriptionResponse("User with id=[%s] %s event with id=[%s]"
                .formatted(request.getUserId(), request.getStatus().equals(SUBSCRIBE) ? "subscribed to" : "unsubscribed from", request.getEventId()),
                request.getEventId(), request.getUserId(), request.getStatus());
    }

}
