package org.sglnu.eventservice.service;

import com.querydsl.core.types.Predicate;
import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.domain.UserEvent;
import org.sglnu.eventservice.dto.*;
import org.sglnu.eventservice.exception.EventIsFullException;
import org.sglnu.eventservice.exception.EventNotFoundException;
import org.sglnu.eventservice.mapper.EventMapper;
import org.sglnu.eventservice.repository.EventRepository;
import org.sglnu.userservice.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sglnu.eventservice.repository.UserEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.sglnu.eventservice.common.EventRegistrationStatus.*;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    private final UserEventRepository userEventRepository;

    private final EventMapper eventMapper;


    @Transactional
    public EventResponse save(EventRequest eventRequest) {
        Event event = eventRepository.save(eventMapper.mapToEvent(eventRequest));

        return eventMapper.mapToEventResponse(event);
    }

    public EventResponse getById(Long id) {
        return eventRepository.findById(id)
                .map(eventMapper::mapToEventResponse)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public Page<EventResponse> getAll(Pageable pageable, Predicate filter) {
        return eventRepository.findAll(filter, pageable)
                .map(eventMapper::mapToEventResponse);
    }

    public EventResponse update(Long id, EventRequest eventRequest) {
        Event eventToUpdate = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        eventMapper.updateEventFromRequest(eventRequest, eventToUpdate);
        return eventMapper.mapToEventResponse(eventRepository.save(eventToUpdate));
    }

    @Transactional
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public EventResponses getUserEventsSubscribedTo(Long id){
        List<UserEvent> userEvents = userEventRepository.findByUserId(id);

        List<Event> events = userEvents.stream()
                .map(UserEvent::getEvent)
                .collect(Collectors.toList());

        return eventMapper.mapToEventResponses(events);
    }

    public SuccessfulSubscriptionResponse subscribeToEvent(Long userId, Long eventId, Event eventSubscribeTo){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event of id [%s] couldn't be found".formatted(eventId), eventId, "Event"));

        if (event.getCurrentParticipants().equals(event.getMaxParticipants())) {
            throw new EventIsFullException("Event is full", eventId, event.getMaxParticipants());
        }

        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new UserNotFoundException("User event not found"));

        userEvent.setEvent(event);

        Boolean isPaid = eventSubscribeTo.getIsPaid();

        if (isPaid.toString().equals("true")) {
            userEvent.setStatus(PENDING);
        } else {
            userEvent.setStatus(APPROVED);
        }

        userEventRepository.save(userEvent);

        return new SuccessfulSubscriptionResponse("User with id=[%s] has been successfully subscribed to the " +
                "event with id=[%s]".formatted(userId, eventId), eventId, userId, APPROVED);
    }

    public SuccessfulUnsubscriptionResponse unsubscribeFromEvent(Long userId, Long eventId){
        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new IllegalArgumentException("User event not found"));


        userEventRepository.delete(userEvent);

        return new SuccessfulUnsubscriptionResponse(("User with id=[%s] has been successfully unsubscribed from event" +
                " with id=[%s]").formatted(userId, eventId), eventId, userId, UNSUBSCRIBED);
    }

    @Transactional
    public UserEvent approveUser(Long userId, Long eventId) {
        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new IllegalArgumentException("User event not found"));

        userEvent.setStatus(APPROVED);

        return userEventRepository.save(userEvent);
    }

    @Transactional
    public UserEvent rejectUser(Long userId, Long eventId) {
        UserEvent userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new IllegalArgumentException("User event not found"));

        userEvent.setStatus(REJECTED);

        return userEventRepository.save(userEvent);
    }

}
