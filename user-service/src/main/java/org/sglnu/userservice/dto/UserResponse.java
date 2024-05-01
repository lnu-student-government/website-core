package org.sglnu.userservice.dto;

import lombok.Builder;
import org.sglnu.userservice.common.Faculty;

@Builder
public record UserResponse(Long id,
                           String email,
                           Faculty faculty,
                           String firstName,
                           String lastName,
                           String groupName,
                           String phoneNumber,
                           Long avatarId) {
}
