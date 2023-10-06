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

    @ManyToOne
    private Salon salon;

    private Long price;
    private Long minutes;
    private TreatmentType treatmentType;

    @OneToMany(
            mappedBy = "treatment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Appointment> appointments;

    @ManyToMany
    @JoinTable(
            name = "master_treatments",
            joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "master_id", referencedColumnName = "id")
    )
    private List<Master> masters;
}
