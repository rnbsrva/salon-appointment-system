package com.akerke.authservice.entity;

import com.akerke.authservice.constants.SecurityRole;
import com.akerke.authservice.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role extends DateAudit {

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
    @JsonIgnore
    private User user;

    public Role(SecurityRole role, User user) {
        this.role = role;
        this.user = user;
    }

}
