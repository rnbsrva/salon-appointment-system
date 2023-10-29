package com.akerke.salonservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startTime;
    private Date endTime;
    private Boolean isBreak;
    @ManyToOne
    @JsonProperty("workDayId")
    private WorkDay workDay;

    public WorkTime(Date startTime, Date endTime, Boolean isBreak, WorkDay workDay) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBreak = isBreak;
        this.workDay = workDay;
    }

    @JsonGetter("workDayId")
    public Long getWorkDayId(){
        return workDay.getId();
    }

}
