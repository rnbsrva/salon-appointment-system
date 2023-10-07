package com.akerke.salonservice.entity;

import com.akerke.salonservice.constants.Status;
import com.akerke.salonservice.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Appointment extends DateAudit {
    @Id
    private Long id;

    @ManyToOne
    private Treatment treatment;
    @ManyToOne
    private User user;
    @ManyToOne
    private Master master;

    @OneToOne
    private WorkTime workTime;

    private Status status;
    private String note;

}
