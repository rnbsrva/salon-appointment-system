package com.akerke.salonservice.domain.entity;

import com.akerke.salonservice.common.constants.TreatmentType;
import com.akerke.salonservice.domain.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String name;

    @ManyToOne
    @JsonProperty("salonId")
    private Salon salon;

    private Long price;
    private Long minutes;
    private TreatmentType treatmentType;

    @OneToMany(
            mappedBy = "treatment",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Appointment> appointments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "master_treatments",
            joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "master_id", referencedColumnName = "id")
    )
    private List<Master> masters;

    @JsonGetter("salonId")
    public Long getSalonId(){
        return salon.getId();
    }
}
