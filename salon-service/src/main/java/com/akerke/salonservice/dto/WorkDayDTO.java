package com.akerke.salonservice.dto;

import com.akerke.salonservice.constants.WeekDay;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
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