package com.biblestudy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibleStudySession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int Duration;
    private String readerStarter;
    private LocalDate nextDate;
    private LocalDate dueDate;
    private WeekRatio weekRatio;
    private Boolean completed;
    @Column(length = 65535)
    private ArrayList<User> members;
    @Column(length = 65535)
    private ArrayList<Invitation> invites;
    @Column(length = 65535)
    private Bible bible;
    private Date expiryTime;
    // getters and setters

}
