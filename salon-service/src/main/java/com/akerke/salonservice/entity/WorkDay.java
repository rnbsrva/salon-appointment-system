package com.akerke.salonservice.entity;

import com.akerke.salonservice.constants.WeekDay;
import com.akerke.salonservice.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
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
    private Date workStartTime;
    private Date workEndTime;
    private Boolean isHoliday;
    @ManyToOne
    private Salon salon;

    @OneToMany(
            mappedBy = "workDay",
            cascade = CascadeType.ALL,
            orphanRemoval = true

    )
    private List<WorkTime> workTimes;

}
