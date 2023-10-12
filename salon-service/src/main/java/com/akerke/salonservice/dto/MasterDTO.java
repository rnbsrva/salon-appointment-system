package com.akerke.salonservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record MasterDTO (
        @NotNull
        Long userId,
        @NotNull
        Long salonId,
        @NotBlank @Size(min=2, max = 100)
        String position,
        Date experienceDate,
        String about
){
}
