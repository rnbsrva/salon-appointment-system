package com.akerke.salonservice.domain.entity;

import com.akerke.salonservice.common.constants.WeekDay;
import com.akerke.salonservice.domain.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonProperty("salonId")
    private Salon salon;

    @ManyToOne
    @JsonProperty("masterId")
    private Master master;

    @OneToMany(
            mappedBy = "workDay",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<WorkTime> workTimes;

    @JsonGetter("salonId")
    public Long getSalonId(){
        return salon.getId();
    }

    @JsonGetter("masterId")
    public Long getMasterId(){
        return master.getId();
    }

}

