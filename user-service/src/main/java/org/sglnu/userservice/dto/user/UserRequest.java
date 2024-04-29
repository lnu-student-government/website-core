package org.sglnu.userservice.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserRequest {

    public static final String EMAIL_REGEX =
            "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

    public static final String PHONE_NUMBER_REGEX =
            "^(\\+\\d{1,3})?[- .]?(\\(\\d{1,3}\\)|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";

    public static final String PASSWORD_REGEX =
            "(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])[A-Za-z\\d]{8,}";

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

    private Long avatarId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
