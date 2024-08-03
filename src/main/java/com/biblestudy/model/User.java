package com.biblestudy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private int missedDays;
    private long bibleStudySessionId;
    @ManyToMany
    @JoinTable(name = "user_friends", // The name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key for User
            inverseJoinColumns = @JoinColumn(name = "friend_id") // Foreign key for Friendship
    )
    private List<Friendship> friends;

    @ManyToMany(mappedBy = "participants")
    private List<CallSession> callSessions;
}
