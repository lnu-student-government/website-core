package com.sglnu.eventservice.dto;

import com.sglnu.core.domain.models.Category;
import com.sglnu.core.domain.models.User;
import lombok.Builder;

import java.util.List;

@Builder
public record EventResponse(String name,
                            String description,
                            String location,
                            Long photoId,
                            String photoLink,
                            String date,
                            String createdAt,
                            String updatedAt,
                            List<User> users,
                            List<Category> categories) {}
