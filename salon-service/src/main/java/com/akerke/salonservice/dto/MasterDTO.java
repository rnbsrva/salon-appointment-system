package com.akerke.salonservice.dto;

import java.util.Date;

public record MasterDTO (
        Long userId,
        Long salonId,
        String position,
        Date experienceDate,
        String about
){
}
