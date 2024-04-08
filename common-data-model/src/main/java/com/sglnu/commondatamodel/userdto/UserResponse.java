package com.sglnu.commondatamodel.userdto;

import lombok.Builder;

@Builder
public record UserResponse(String email,
                           String password,
                           String faculty,
                           String firstName,
                           String lastName,
                           String groupName,
                           String phoneNumber,
                           Long avatarId) {}
