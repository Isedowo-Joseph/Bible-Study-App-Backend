package com.biblestudy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibleStudySession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private int Duration;
    private String readerStarter;
    private LocalDate nextDate;
    private LocalDate dueDate;
    private WeekRatio weekRatio;
    private Boolean completed;
    private ArrayList<User> members;
    private ArrayList<Invitation> invites;
    private Bible bible;

    // getters and setters

}
