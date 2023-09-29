package com.akerke.salonservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WorkTime {
    @Id
    private Long id;

    private Date startTime;
    private Date endTime;
    private Boolean isBreak;
    @ManyToOne
    private WorkDay workDay;

    public WorkTime(Date startTime, Date endTime, Boolean isBreak, WorkDay workDay) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBreak = isBreak;
        this.workDay = workDay;
    }

}
