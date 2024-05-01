package org.sglnu.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sglnu.userservice.client.dto.UserCategoryResponse;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private UserResponse user;
    private List<UserCategoryResponse> assignedCategories;
    private TokenResponse token;

}
