package com.biblestudy.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibleStudySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private int Duration;
    private String readerStarter;
    private String currentChapter;
    private LocalDate nextDate;
    private LocalDate dueDate;
    private WeekRatio weekRatio;
    private Boolean completed;
    private ArrayList<User> group;
    private ArrayList<Invitation> invites;

    public void addMember(User u){

    }
    public void removeMember(User u){

    }
    public void inviteUser(long id, String status, User friendship){

    }
    public BibleStudySession saveSession(BibleStudySession bs){

        return bs;
    }
    public BibleStudySession findByid(Long id){

        return null;
    }
    public ArrayList<BibleStudySession> findAll(){

        return null;
    }
    public void deleteByid(long id){

    }
    public void updateSession(long id, BibleStudySession sessionDetails){


    }
}
