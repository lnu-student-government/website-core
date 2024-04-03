package com.sglnu.userservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import static com.sglnu.userservice.dto.UserRequest.*;

@Data
@Builder
public class RegisterRequest {

    @NotBlank(message = "Email cannot be empty!")
    @Email(regexp = EMAIL_REGEX, flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email has to be in a valid format!")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    @Pattern(regexp = PASSWORD_REGEX,
            message = "Must be minimum 8 symbols long, using digits and latin letters," +
                    " containing at least one digit, one uppercase letter, and one lowercase letter")
    private String password;

    private String faculty;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter")
    private String lastName;

    @NotBlank(message = "Group name cannot be empty!")
    private String groupName;

    @NotBlank(message = "Phone number cannot be empty!")
    @Pattern(regexp = PHONE_NUMBER_REGEX,
            message = "Phone number has to be in a valid format!")
    private String phoneNumber;

}
