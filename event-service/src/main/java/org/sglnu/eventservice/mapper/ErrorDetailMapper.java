package org.sglnu.eventservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sglnu.eventservice.dto.ErrorDetail;
import org.sglnu.eventservice.exception.EventNotFoundException;

@Mapper(componentModel = "spring")
public interface ErrorDetailMapper {

    @Mapping(target = "cause", expression = "java(\"id\")")
    @Mapping(target = "message", expression = "java(\"Entity with id=[%s] hasn't been found\".formatted(ex.getEventId()))")
    ErrorDetail from(EventNotFoundException ex);

}
