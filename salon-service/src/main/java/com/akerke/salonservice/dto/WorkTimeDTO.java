package com.akerke.salonservice.dto;

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
