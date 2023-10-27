package com.akerke.authservice.domain.entity;

import com.akerke.authservice.common.constants.Gender;
import com.akerke.authservice.domain.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_entity")
@Getter
@Setter
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String phone;
    private Boolean emailVerified;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonIgnore
    private String password;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private List<Role> roles;
}