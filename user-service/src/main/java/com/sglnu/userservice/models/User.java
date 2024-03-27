package com.sglnu.userservice.models;

import com.sglnu.eventservice.models.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "faculty")
    private String faculty;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "avatar_id")
    private BigInteger avatarId;
    @Column(name = "created_at")
    private BigInteger created_at;
    @Column(name = "updated_at")
    private BigInteger updated_at;

    @ManyToMany(mappedBy = "users")
    private List<Event> events;

    @ManyToMany(mappedBy = "users")//Category
    private List<Category> categories;

}
