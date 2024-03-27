
package com.sglnu.eventservice.models;


import com.sglnu.userservice.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@AllArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event eventId;
    @Column(name = "visited")
    private boolean visited;
    @Column(name = "receive_photos")
    private boolean receivePhotos;
    @Column(name = "comment")
    private String comment;
    public Feedback() {

    }
}