package com.sglnu.eventservice.mapper;

import com.sglnu.core.domain.models.Event;
import com.sglnu.eventservice.dto.EventRequest;
import com.sglnu.eventservice.dto.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface EventMapper {

    Event mapToEvent(EventRequest eventRequest);

    EventResponse mapToEventResponse(Event event);

}
