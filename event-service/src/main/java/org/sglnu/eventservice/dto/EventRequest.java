package org.sglnu.eventservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventRequest {

    private String name;
    private String description;
    private LocalDateTime date;
    private String location;
    private Boolean isPaid;
    private BigDecimal price;
    private Integer maxParticipants;

}
