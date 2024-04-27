package org.sglnu.eventservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sglnu.eventservice.dto.ErrorDetail;
import org.sglnu.eventservice.exception.EventIsFullException;
import org.sglnu.eventservice.exception.EventNotFoundException;
import org.sglnu.eventservice.exception.UserIsAlreadySubscribed;

@Mapper(componentModel = "spring")
public interface ErrorDetailMapper {

    @Mapping(target = "cause", expression = "java(\"id\")")
    @Mapping(target = "message", expression = "java(\"Event of id=[%s] hasn't been found\".formatted(ex.getEventId()))")
    ErrorDetail from(EventNotFoundException ex);

    @Mapping(target = "cause", expression = "java(\"size\")")
    @Mapping(target = "message", expression = "java(\"Event's size with id=[%s] is full\".formatted(ex.getEventId()))")
    ErrorDetail from(EventIsFullException ex);

    @Mapping(target = "cause", expression = "java(\"id\")")
    @Mapping(target = "message", expression = "java(\"User of id=[%s] is already subscribed to Event " +
            "of id=[%s]\".formatted(ex.getUserId(), ex.getEventId()))")
    ErrorDetail from(UserIsAlreadySubscribed ex);

}
