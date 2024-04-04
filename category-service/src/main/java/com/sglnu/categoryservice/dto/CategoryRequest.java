package com.sglnu.categoryservice.dto;

import com.sglnu.core.domain.models.Event;
import com.sglnu.core.domain.models.User;
import lombok.Data;

import java.util.List;

@Data
public class CategoryRequest{

    private String name;
    private List<User> users;
    private List<Event> events;

}
