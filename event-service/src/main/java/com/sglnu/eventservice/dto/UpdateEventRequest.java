package com.sglnu.eventservice.dto;

import com.sglnu.core.domain.models.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UpdateEventRequest {

    private String name;
    private String description;
    private String location;
    private Long photoId;
    private LocalDateTime date;
    private List<Category> categories;

}
