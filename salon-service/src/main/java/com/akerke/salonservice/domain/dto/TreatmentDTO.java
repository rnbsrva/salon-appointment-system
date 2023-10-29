package com.akerke.salonservice.domain.dto;

import com.akerke.salonservice.common.constants.TreatmentType;
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
