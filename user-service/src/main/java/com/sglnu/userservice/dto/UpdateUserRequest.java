package com.sglnu.userservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

    private String faculty;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter")
    private String lastName;

    private String groupName;

    @Pattern(regexp = UserRequest.PHONE_NUMBER_REGEX,
            message = "Phone number has to be in a valid format!")
    private String phoneNumber;

    private Long avatarId;

}
