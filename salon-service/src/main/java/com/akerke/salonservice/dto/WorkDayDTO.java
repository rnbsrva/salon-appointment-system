package com.akerke.salonservice.dto;

import com.akerke.salonservice.constants.WeekDay;

import java.util.Date;

public record WorkDayDTO(
        WeekDay weekDay,
        Date startTime,
        Date endTime,
        Boolean isHoliday
) {
}
