package org.sglnu.userservice.dto.usercategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCategory {
    private Long id;
    private Long userId;
    private Long categoryId;
}
