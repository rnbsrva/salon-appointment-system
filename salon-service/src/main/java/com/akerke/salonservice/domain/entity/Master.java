package com.akerke.salonservice.domain.entity;

import com.akerke.salonservice.domain.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Master extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonProperty("userId")
    private User user;

    private String position;

    private Date experienceDate;

    private String about;

    @ManyToOne
    @JsonProperty("salonId")
    private Salon salon;

    @ManyToMany
    @JoinTable(
            name = "master_treatments",
            joinColumns = @JoinColumn(name = "master_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id")
    )
    @JsonProperty("treatmentsId")
    private List<Treatment> treatments;

    @OneToMany(
            mappedBy ="master",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Appointment> appointments;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<WorkTime> workTimes;


    @OneToMany(
            mappedBy = "master",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Feedback> feedbacks;


    @JsonGetter("salonId")
    public Long getSalonId(){
        return salon.getId();
    }

    @JsonGetter("userId")
    public Long getUserId(){
        return user.getId();
    }

    @JsonGetter("treatmentsId")
    public List<Long> getTreatmentsId() {
        return treatments.stream().map(Treatment::getId).collect(Collectors.toList());
    }

}
