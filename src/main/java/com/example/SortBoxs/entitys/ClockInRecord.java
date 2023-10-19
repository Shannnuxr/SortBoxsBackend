package com.example.SortBoxs.entitys;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ClockInRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String comment;
    private Date clockInTime;
    private String location;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    //for clock out time
    private Date clockOutTime;
    private Long totalTimeWorkedInMinutes;

    public void setTotalTimeInMinutes(long totalMinutes) {
        this.totalTimeWorkedInMinutes = totalMinutes;
    }
}
