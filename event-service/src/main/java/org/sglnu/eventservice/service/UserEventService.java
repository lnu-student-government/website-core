package org.sglnu.eventservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.domain.UserEvent;
import org.sglnu.eventservice.dto.EventResponses;
import org.sglnu.eventservice.dto.SuccessfulSubscriptionResponse;
import org.sglnu.eventservice.dto.SuccessfulUnsubscriptionResponse;
import org.sglnu.eventservice.dto.UserEventResponse;
import org.sglnu.eventservice.exception.EventIsFullException;
import org.sglnu.eventservice.exception.EventNotFoundException;
import org.sglnu.eventservice.exception.UserIsAlreadySubscribed;
import org.sglnu.eventservice.exception.UserIsNotSubscribedToEvent;
import org.sglnu.eventservice.mapper.EventMapper;
import org.sglnu.eventservice.mapper.UserEventMapper;
import org.sglnu.eventservice.repository.EventRepository;
import org.sglnu.eventservice.repository.UserEventRepository;
import org.sglnu.userservice.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.sglnu.eventservice.common.EventRegistrationStatus.*;

@RequiredArgsConstructor
@Service
public class UserEventService {

    private final UserEventRepository userEventRepository;

    private final EventRepository eventRepository;

    private final UserEventMapper userEventMapper;

    private final EventMapper eventMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserEventService.class);

    public EventResponses getUserEventsSubscribedTo(Long userId){
        logger.info("Fetching events subscribed by user with id={}", userId);

        List<UserEvent> userEvents = userEventRepository.findByUserId(userId);

        List<Event> events = userEvents.stream()
                .map(UserEvent::getEvent)
                .collect(Collectors.toList());

        logger.info("Fetched {} events subscribed by user with id={}", events.size(), userId);

        return eventMapper.mapToEventResponses(events);
    }

    @Transactional
    public SuccessfulSubscriptionResponse subscribeToEvent(Long userId, Long eventId){
        logger.info("Attempting to subscribe user with id={} to event with id={}", userId, eventId);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    logger.error("Event with id={} not found", eventId);
                    return new EventNotFoundException("Event of id [%s] couldn't be found".formatted(eventId), eventId, "Event");
                });

        if (event.getCurrentParticipants().equals(event.getMaxParticipants())) {
            logger.error("Event with id={} is full", eventId);
            throw new EventIsFullException("Event is full", eventId, event.getMaxParticipants());
        }

        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> {
                    logger.error("User with id={} is already subscribed to event with id={}", userId, eventId);
                    return new UserIsAlreadySubscribed(("User of id [%s] is already subscribed to Event of" +
                            "id=[%s]").formatted(userId, eventId), userId, eventId);
                });

        userEvent.setEvent(event);

        Boolean isPaid = event.getIsPaid();

        if (isPaid) {
            userEvent.setStatus(PENDING);
            userEventRepository.save(userEvent);

            logger.info("User with id={} has been successfully subscribed to the event with id={}", userId, eventId);

            return new SuccessfulSubscriptionResponse("User of id=[%s] has been successfully subscribed to the " +
                    "event of id=[%s]".formatted(userId, eventId), eventId, userId, PENDING);
        } else {
            userEventRepository.save(userEvent);
            userEvent.setStatus(APPROVED);

            logger.info("User with id={} has been successfully subscribed to the event with id={}", userId, eventId);

            return new SuccessfulSubscriptionResponse("User of id=[%s] has been successfully subscribed to the " +
                    "event of id=[%s]".formatted(userId, eventId), eventId, userId, APPROVED);
        }
    }

    @Transactional
    public SuccessfulUnsubscriptionResponse unsubscribeFromEvent(Long userId, Long eventId){
        logger.info("Attempting to unsubscribe user with id={} from event with id={}", userId, eventId);

        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> {
                    logger.error("User event not found for user with id={} and event with id={}", userId, eventId);
                    return new UserIsNotSubscribedToEvent("User event not found", userId, eventId);
                });

        userEventRepository.delete(userEvent);
        logger.info("User with id={} has been successfully unsubscribed from the event with id={}", userId, eventId);

        return new SuccessfulUnsubscriptionResponse(("User of id=[%s] has been successfully unsubscribed from the " +
                "event of id=[%s]").formatted(userId, eventId), eventId, userId, UNSUBSCRIBED);
    }

    @Transactional
    public UserEventResponse approveUser(Long userId, Long eventId) {
        logger.info("Attempting to approve user with id={} for event with id={}", userId, eventId);

        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> {
                    logger.error("User event not found for user with id={} and event with id={}", userId, eventId);
                    return new UserIsNotSubscribedToEvent("User event not found", userId, eventId);
                });

        userEvent.setStatus(APPROVED);

        UserEvent updatedUserEvent = userEventRepository.save(userEvent);
        logger.info("User with id={} has been approved for the event with id={}", userId, eventId);

        return userEventMapper.mapToUserEventResponse(updatedUserEvent);
    }

    @Transactional
    public UserEventResponse rejectUser(Long userId, Long eventId) {
        logger.info("Attempting to reject user with id={} for event with id={}", userId, eventId);

        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> {
                    logger.error("User event not found for user with id={} and event with id={}", userId, eventId);
                    return new UserIsNotSubscribedToEvent("User event not found", userId, eventId);
                });

        userEvent.setStatus(REJECTED);

        userEventRepository.save(userEvent);
        logger.info("User with id={} has been rejected for the event with id={}", userId, eventId);

        return userEventMapper.mapToUserEventResponse(userEvent);
    }

}
