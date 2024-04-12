package org.sglnu.mediaservice.dto;

import org.sglnu.mediaservice.domain.datatype.InteractionType;

public record FileAccessLink(InteractionType accessType, String url) {
}
