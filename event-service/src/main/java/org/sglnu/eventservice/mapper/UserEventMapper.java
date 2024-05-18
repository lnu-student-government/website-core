package org.sglnu.eventservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.sglnu.eventservice.domain.UserEvent;
import org.sglnu.eventservice.dto.UserEventResponse;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserEventMapper {

    UserEventResponse mapToUserEventResponse(UserEvent userEvent);

}
