package com.akerke.authservice.entity;

import com.akerke.authservice.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;

    private Boolean emailVerified;

    @JsonIgnore
    private String password;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user"
    )
    private List<Role> roles;
}
