package com.sglnu.eventservice.service;

import com.querydsl.core.types.Predicate;
import com.sglnu.eventservice.dto.EventRequest;
import com.sglnu.eventservice.dto.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {

    EventResponse save(EventRequest eventRequest);

    EventResponse getById(Long id);

    Page<EventResponse> getAll(Pageable pageable, Predicate filter);

    EventResponse update(Long id, EventRequest eventRequest);

    void delete(Long id);

}
