package com.biblestudy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private int missedDays;
    private Long bibleStudySessionId;

    @Lob
    private String profileImage;

    @OneToMany(mappedBy = "participants", cascade = CascadeType.REMOVE) // Use mappedBy for bidirectional
    @JsonIgnore
    private List<CallSession> callSessions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_FRIENDS", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @JsonIgnore
    private List<Friendship> friends;

    @OneToMany
    @JsonIgnore
    private List<Invitation> invites;

    private Boolean hasJoined = false;

    @OneToOne
    private Membership membership;
}
