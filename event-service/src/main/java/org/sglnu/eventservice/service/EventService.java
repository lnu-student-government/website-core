package org.sglnu.eventservice.service;

import com.querydsl.core.types.Predicate;
import org.sglnu.eventservice.common.EventRegistrationStatus;
import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.domain.UserEvent;
import org.sglnu.eventservice.dto.EventRequest;
import org.sglnu.eventservice.dto.EventResponse;
import org.sglnu.eventservice.dto.EventResponses;
import org.sglnu.eventservice.mapper.EventMapper;
import org.sglnu.eventservice.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sglnu.eventservice.repository.UserEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public EventResponse subscribeToEvent(Long userId, Long eventId, Event eventSubscribeTo){
        Optional<Event> event = eventRepository.findById(eventId);
        if(event.isEmpty()){
            throw new IllegalArgumentException("Event not found");
        }

        Optional<UserEvent> userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId);
        if(userEvent.isPresent()){
            throw new IllegalArgumentException("User already subscribed to event");
        }

        UserEvent newUserEvent = new UserEvent();
        newUserEvent.setUserId(userId);
        newUserEvent.setEvent(event.get());

        Boolean isPaid = eventSubscribeTo.getIsPaid();

        switch (isPaid.toString()) {
            case "true":
                newUserEvent.setStatus(PENDING);
                break;
            case "false":
                newUserEvent.setStatus(APPROVED);
                break;
            default:
                throw new IllegalArgumentException("Event is paid, but no payment details provided");
        }


        return eventMapper.mapToEventResponse(event.get());
    }
    public EventResponse unsubscribeFromEvent(Long userId, Long eventId){
        Optional<UserEvent> userEvent = userEventRepository.findByUserIdAndEventId(userId, eventId);
        if(userEvent.isEmpty()){
            throw new IllegalArgumentException("User not subscribed to event");
        }

        userEventRepository.delete(userEvent.get());

        return eventRepository.findById(eventId)
                .map(eventMapper::mapToEventResponse)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    @Transactional
    public UserEvent approveUser(Long id) {
        UserEvent userEvent = userEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User event not found"));

        userEvent.setStatus(APPROVED);

        return userEventRepository.save(userEvent);
    }

    @Transactional
    public UserEvent rejectUser(Long id) {
        UserEvent userEvent = userEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User event not found"));

        userEvent.setStatus(REJECTED);

        return userEventRepository.save(userEvent);
    }

}
