package com.akerke.salonservice.domain.dto;

import java.util.List;

public record AddTreatmentDTO(
        List<Long> treatmentIds
) {
}
