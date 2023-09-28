package com.akerke.salonservice.entity;

import com.akerke.salonservice.constants.WeekDay;
import com.akerke.salonservice.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "work_day")
public class WorkDay extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private WeekDay weekDay;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private Boolean isHoliday;

    @OneToMany(
            mappedBy = "work_day",
            cascade = CascadeType.ALL
    )
    private List<WorkTime> workTimeList;

    public WorkDay(WeekDay weekDay, LocalDateTime workStartTime, LocalDateTime workEndTime, Boolean isHoliday) {
        this.weekDay = weekDay;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.isHoliday = isHoliday;
    }
}
