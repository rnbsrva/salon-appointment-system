package com.akerke.salonservice.entity;

import com.akerke.salonservice.constants.TreatmentType;
import com.akerke.salonservice.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Treatment extends DateAudit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;
    private Long minutes;
    private TreatmentType treatmentType;
    @OneToMany(
            mappedBy = "treatment",
            cascade = CascadeType.ALL
    )
    private List<Appointment> appointments;

    public Treatment(Long price, Long minutes,  TreatmentType treatmentType) {
        this.price = price;
        this.minutes = minutes;
        this.treatmentType = treatmentType;
    }
}
