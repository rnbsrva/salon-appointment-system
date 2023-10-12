package com.akerke.salonservice.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("userId")
    private User user;

    @ManyToOne
    @JsonProperty("masterId")
    private Master master;

    private Integer rating;

    @ManyToOne
    @JsonProperty("appointmentId")
    private Appointment appointment;

    private String feedbackText;

    @JsonGetter("masterId")
    public Long getMasterId(){
        return master.getId();
    }

    @JsonGetter("userId")
    public Long getUserId(){
        return user.getId();
    }

    @JsonGetter("appointmentId")
    public Long getAppointmentId(){
        return appointment.getId();
    }

}
