package com.akerke.salonservice.domain.entity;

import com.akerke.salonservice.domain.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @OneToOne
    private Address address;

    @Column( length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonProperty("ownerId")
    private User owner;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "salon",
            cascade = CascadeType.ALL
    )
    private List<Master> masters;

    @OneToMany(
            mappedBy = "salon",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Treatment> treatments;

    @OneToMany(
            mappedBy = "salon",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<WorkDay> workDays;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<ImageMetadata> imageMetadata;


    @JsonGetter("ownerId")
    public Long getUserId(){
        return owner.getId();
    }

}
