package com.biblestudy.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// to define #days on odd&even week for bible study 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekRatio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int oddWeek;
    private int evenWeek;

    @Override
    public String toString() {
        return oddWeek + ":" + evenWeek;
    }
}
