package org.sglnu.userservice.dto;

import lombok.Builder;

@Builder
public record UserResponse(String email,
                           String password,
                           String faculty,
                           String firstName,
                           String lastName,
                           String groupName,
                           String phoneNumber,
                           Long avatarId)
{
}
