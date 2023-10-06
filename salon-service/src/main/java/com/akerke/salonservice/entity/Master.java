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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String position;

    private Date experienceDate;

    private String about;

    @ManyToOne
    private Salon salon;

    @ManyToMany
    @JoinTable(
            name = "master_treatments",
            joinColumns = @JoinColumn(name = "master_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id")
    )
    private List<Treatment> treatments;

    @OneToMany(
            mappedBy ="master",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Appointment> appointments;

    @OneToMany(
            mappedBy = "master",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WorkTime> workTimes;


    @OneToMany(
            mappedBy = "master",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Feedback> feedbacks;

}
