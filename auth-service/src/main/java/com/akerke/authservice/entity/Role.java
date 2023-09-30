package com.akerke.authservice.entity;

import com.akerke.authservice.constants.SecurityRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SecurityRole role;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "role",
            cascade = CascadeType.ALL
    )
    private Set<Permission> permissions;

    @ManyToOne
    @JoinColumn
    private User user;

}
