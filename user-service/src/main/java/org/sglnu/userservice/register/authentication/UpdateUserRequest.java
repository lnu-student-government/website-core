package org.sglnu.userservice.register.authentication;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.sglnu.userservice.common.Faculty;
import org.sglnu.userservice.dto.user.UserRequest;

@Data
@Builder
public class UpdateUserRequest {

    private Faculty faculty;

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

}
