package com.sglnu.eventservice.dto;

import com.sglnu.core.domain.models.Category;
import com.sglnu.core.domain.models.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventRequest {

    private String name;
    private String description;
    private String location;
    private Long photoId;
    private LocalDateTime date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<User> users;
    private List<Category> categories;

}
