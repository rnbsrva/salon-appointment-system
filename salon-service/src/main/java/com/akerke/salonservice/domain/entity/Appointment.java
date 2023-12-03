package com.akerke.salonservice.domain.entity;

import com.akerke.salonservice.common.constants.Status;
import com.akerke.salonservice.domain.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Appointment extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonProperty("treatmentId")
    private Treatment treatment;
    @ManyToOne
    @JsonProperty("userId")
    private User user;
    @ManyToOne()
    @JsonProperty("masterId")
    private Master master;

    @OneToOne
    private WorkTime workTime;

    private Status status;
    private String note;


    @JsonGetter("treatmentId")
    public Long getTreatmentId(){
        return treatment.getId();
    }

    @JsonGetter("userId")
    public Long getUserId(){
        return user.getId();
    }

    @JsonGetter("masterId")
    public Long getMasterId(){
        return master.getId();
    }
}
