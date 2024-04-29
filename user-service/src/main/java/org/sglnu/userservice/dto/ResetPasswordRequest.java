package org.sglnu.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static org.sglnu.userservice.dto.user.UserRequest.PASSWORD_REGEX;

public class ResetPasswordRequest {

    @NotBlank(message = "Password cannot be empty!")
    private String currentPassword;

    @NotBlank(message = "Password cannot be empty!")
    @Pattern(regexp = PASSWORD_REGEX,
            message = "Must be minimum 8 symbols long, using digits and latin letters," +
                    " containing at least one digit, one uppercase letter, and one lowercase letter")
    private String newPassword;

    @NotBlank
    private String confirmPassword;

}
