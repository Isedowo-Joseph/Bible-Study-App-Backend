package com.biblestudy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userName;
    private String password;
    private String email;
    private int phoneNumber;
    private int missedDays;
    private long bibleStudySession;
    private ArrayList<Friendship> friends;
    @ManyToMany(mappedBy = "participants")
    private List<CallSession> callSessions = new ArrayList<>();

    public void registerUser(String name, String username, String password, String email, String phoneNumber){

    }
    public Boolean loginUser(String username, String password){
        return null;
    }

    public User findByUsername(String s){
        return null;
    }

}
