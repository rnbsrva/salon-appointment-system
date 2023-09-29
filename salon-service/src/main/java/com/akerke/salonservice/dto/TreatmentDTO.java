package com.akerke.salonservice.dto;

import com.akerke.salonservice.constants.TreatmentType;

public record TreatmentDTO(
        Long price,
        Long minutes,
        TreatmentType treatmentType
) {
}
