package com.biblestudy.model;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekRatio {
    private int oddWeek;
    private int evenWeek;

    @Override
    public String toString() {
        return oddWeek + ":" + evenWeek;
    }
}

