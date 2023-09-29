package com.akerke.salonservice.entity;

import com.akerke.salonservice.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Master extends DateAudit {

    @Id
    private Long id;

    @OneToOne
    private User user;

    private String position;

    private Date experienceDate;

    private String about;

    @ManyToOne
    private Salon salon;

    @OneToMany(
            mappedBy ="master",
            cascade = CascadeType.ALL
    )
    private List<Treatment> treatments;

    @OneToMany(
            mappedBy ="master",
            cascade = CascadeType.ALL
    )
    private List<Appointment> appointments;

    @OneToMany(
            mappedBy = "master",
            cascade = CascadeType.ALL
    )
    private List<WorkTime> workTimes;

}
