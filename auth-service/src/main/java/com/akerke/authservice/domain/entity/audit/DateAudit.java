package com.akerke.authservice.domain.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class DateAudit {

    @JsonIgnore
    @Column(
            name = "created_time",
            updatable = false
    )
    private LocalDateTime createdTime;

    @JsonIgnore
    @Column(
            name = "last_modified_time"
    )
    private LocalDateTime lastModifiedTime;

    @PrePersist
    private void prePersist() {
        this.createdTime = LocalDateTime.now();
    }

    @PostUpdate
    private void postUpdate() {
        this.lastModifiedTime = LocalDateTime.now();
    }

}