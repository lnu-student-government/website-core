package org.sglnu.eventservice.mapper;

import org.sglnu.eventservice.domain.Event;
import org.sglnu.eventservice.dto.EventRequest;
import org.sglnu.eventservice.dto.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface EventMapper {

    Event mapToEvent(EventRequest eventRequest);
    EventResponse mapToEventResponse(Event event);

}
