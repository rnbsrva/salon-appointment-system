package com.akerke.salonservice.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record WorkTimeDTO(
        Date startTime,
        Date endTime,
        Boolean isBreak,
        @NotNull
        Long workDayId
) {
}
