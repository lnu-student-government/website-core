package org.sglnu.eventservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {

    private String name;
    private String description;
    private LocalDateTime date;
    private String location;
    private Boolean isPaid;
    private Double price;
    private Integer maxParticipants;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
