package com.akerke.authservice.entity;

import com.akerke.authservice.constants.Scope;
import com.akerke.authservice.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Permission  extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Scope type;

    private Long target;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Role role;

    public Permission(Scope type,Role role) {
        this.type = type;
        this.role = role;
    }
}

