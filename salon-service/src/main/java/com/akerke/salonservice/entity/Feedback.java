package com.akerke.salonservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feedback {
    @Id
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Master master;

    private Integer rating;

    @ManyToOne
    private Appointment appointment;

    private String feedbackText;

}
