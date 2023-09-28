package com.akerke.salonservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WorkTime {
    @Id
    private Long id;

    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Boolean isBreak;

    public WorkTime(LocalDateTime start_time, LocalDateTime end_time, Boolean isBreak) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.isBreak = isBreak;
    }

}
