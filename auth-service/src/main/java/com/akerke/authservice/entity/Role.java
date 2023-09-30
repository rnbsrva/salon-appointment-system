package com.akerke.authservice.entity;

import com.akerke.authservice.constants.SecurityRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SecurityRole role;

    @ManyToOne
    @JoinColumn
    private User user;
}
