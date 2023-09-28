package com.akerke.salonservice.entity;

import com.akerke.salonservice.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Salon extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private String address;

    @Column(name = "description", length = 1000)
    private String description;


    @OneToMany(
            mappedBy = "salon",
            cascade = CascadeType.ALL
    )
    private List<Master> masters;

    @OneToMany(
            mappedBy = "salon",
            cascade = CascadeType.ALL
    )
    private List<Treatment> treatments;

    @OneToMany(
            mappedBy = "salon",
            cascade = CascadeType.ALL
    )
    private List<WorkDay> workDays;

    @OneToMany(
            mappedBy = "salon",
            cascade = CascadeType.ALL
    )
    private List<Feedback> feedbacks;

}
