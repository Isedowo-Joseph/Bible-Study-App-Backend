package com.biblestudy.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membership implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Use Long for the foreign key reference to User
    @Column(name = "user_id") // This maps the column name in the database
    private Long userId; // Store the ID of the User

    // Use Long for the foreign key reference to BibleStudySession
    @Column(name = "bible_study_session_id") // This maps the column name in the database
    private Long bibleStudySessionId; // Store the ID of the BibleStudySession
}