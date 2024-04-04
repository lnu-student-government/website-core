package com.sglnu.categoryservice.dto;

import com.sglnu.core.domain.models.Event;
import com.sglnu.core.domain.models.User;

import java.util.List;

public record CategoryResponse(String name,
                              List<User> users,
                              List<Event> events) {}