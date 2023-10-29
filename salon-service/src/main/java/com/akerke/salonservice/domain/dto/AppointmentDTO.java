package com.akerke.salonservice.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AppointmentDTO(
        @NotNull
        Long treatmentId,
        @NotNull
        Long userId,
        @NotNull
        Long masterId,
        @NotNull
        Long workTimeId,
        @Size(min=2, max = 255)
        String note
) {
}
