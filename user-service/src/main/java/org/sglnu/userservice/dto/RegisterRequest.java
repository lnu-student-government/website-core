package org.sglnu.userservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.sglnu.userservice.validator.annotation.ValidFaculty;

import java.util.List;

import static org.sglnu.userservice.dto.UserRequest.EMAIL_REGEX;
import static org.sglnu.userservice.dto.UserRequest.PASSWORD_REGEX;
import static org.sglnu.userservice.dto.UserRequest.PHONE_NUMBER_REGEX;

@Data
@Builder
public class RegisterRequest {

    @NotBlank(message = "Email cannot be empty!")
    @Email(regexp = EMAIL_REGEX, flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email has to be in a valid format!")
    @Column(unique = true)
    private String email;


    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 8, message = "Password should be less than 8 symbols")
    @Pattern(regexp = PASSWORD_REGEX,
            message = "Must be minimum 8 symbols long, using digits and latin letters," +
                    " containing at least one digit, one uppercase letter, and one lowercase letter")
    private String password;

    @NotBlank(message = "Please repeat the password!")
    private String repeatedPassword;

    @ValidFaculty
    @NotNull(message = "Faculty name cannot be empty!")
    private String faculty;

    @NotNull(message = "Name cannot be empty")
    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter")
    private String firstName;

    @NotNull(message = "Surname cannot be empty")
    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter")
    private String lastName;

    @NotBlank(message = "Group name cannot be empty!")
    private String groupName;

    @NotBlank(message = "Phone number cannot be empty!")
    @Pattern(regexp = PHONE_NUMBER_REGEX,
            message = "Phone number has to be in a valid format!")
    private String phoneNumber;

    private List<String> categories;

}
