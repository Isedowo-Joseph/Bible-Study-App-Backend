package com.biblestudy.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.time.LocalDate;
import com.biblestudy.model.WeekRatio;
import com.biblestudy.model.User;
import com.biblestudy.model.Invitation;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibleStudySessionController {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long userId;
    int Duration;
    String readerStarter;
    String currentChapter;
    LocalDate nextDate;
    LocalDate dueDate;
    WeekRatio weekRatio;
    Boolean completed;
    ArrayList<User> group;
    ArrayList<Invitation> invites;

    public void addMember(User u){

    }
    public void removeMember(User u){

    }
    public void inviteUser(long id, String status, User friendship){

    }
}

