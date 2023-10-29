package com.akerke.salonservice.domain.dto;

import com.akerke.salonservice.common.constants.WeekDay;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record WorkDayDTO(
        @NotNull
        Long salonId,
        @NotNull
        Long masterId,
        WeekDay weekDay,
        Date workStartTime,
        Date workEndTime,
        Boolean isHoliday
) {
}