package com.biblestudy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionId;
    @ManyToMany
    @JoinTable(name = "call_session_participants", // Name of the join table
            joinColumns = @JoinColumn(name = "call_session_id"), // Foreign key for CallSession
            inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key for User
    )
    private List<User> participants = new ArrayList<>();
    // getters and setters

}
