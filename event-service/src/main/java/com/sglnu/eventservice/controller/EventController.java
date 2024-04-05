package com.sglnu.eventservice.controller;

import com.querydsl.core.types.Predicate;
import com.sglnu.core.domain.models.Event;
import com.sglnu.eventservice.dto.EventRequest;
import com.sglnu.eventservice.dto.EventResponse;
import com.sglnu.eventservice.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;


    @PostMapping("/new-event")
    public EventResponse create(@Valid @RequestBody EventRequest request, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Error creating user: {}", result.getAllErrors());
            return null;
        }
        return eventService.save(request);
    }

    @GetMapping("/{id}")
    public EventResponse getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @GetMapping
    public Page<EventResponse> getAll(@PageableDefault Pageable pageable,
                                     @QuerydslPredicate(root = Event.class) Predicate filter) {
        return eventService.getAll(pageable, filter);
    }

    @PutMapping("/{id}")
    public EventResponse update(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
        return eventService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        eventService.delete(id);
    }
}
