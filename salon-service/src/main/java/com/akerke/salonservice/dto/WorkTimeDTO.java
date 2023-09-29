package com.akerke.salonservice.dto;

import java.util.Date;

public record WorkTimeDTO(
        Date startTime,
        Date endTime,
        Boolean isBreak,
        Long workDayId
) {
}
