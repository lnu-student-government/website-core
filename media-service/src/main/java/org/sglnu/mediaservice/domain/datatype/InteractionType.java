package org.sglnu.mediaservice.domain.datatype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InteractionType {

    WRITE("w"),
    READ("r"),
    DELETE("d");

    private final String stringRepresentation;
}
