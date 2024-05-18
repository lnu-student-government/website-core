package org.sglnu.eventservice.mapper;

import org.mapstruct.MappingTarget;
import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.dto.EventRequest;
import org.sglnu.eventservice.dto.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.sglnu.eventservice.dto.EventResponses;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface EventMapper {

    Event mapToEvent(EventRequest eventRequest);
    EventResponse mapToEventResponse(Event event);
    void updateEventFromRequest(EventRequest eventRequest, @MappingTarget Event eventToUpdate);
    EventResponses mapToEventResponses(List<Event> events);

}
