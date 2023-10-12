package com.akerke.salonservice.dto;

import com.akerke.salonservice.constants.TreatmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TreatmentDTO(
        @NotNull
        Long salonId,
        @NotBlank
        String name,
        @NotNull
        Long price,
        @NotNull
        Long minutes,
        TreatmentType treatmentType
) {
}
