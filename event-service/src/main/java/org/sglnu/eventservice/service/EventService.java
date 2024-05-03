package org.sglnu.eventservice.service;

import com.querydsl.core.types.Predicate;
import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.domain.UserEvent;
import org.sglnu.eventservice.dto.*;
import org.sglnu.eventservice.exception.EventIsFullException;
import org.sglnu.eventservice.exception.EventNotFoundException;
import org.sglnu.eventservice.exception.UserIsAlreadySubscribed;
import org.sglnu.eventservice.mapper.EventMapper;
import org.sglnu.eventservice.repository.EventRepository;
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

}
