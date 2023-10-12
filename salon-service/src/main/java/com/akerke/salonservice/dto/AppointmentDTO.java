package com.akerke.salonservice.dto;

import com.akerke.salonservice.constants.Status;
import com.akerke.salonservice.entity.Master;
import com.akerke.salonservice.entity.Treatment;
import com.akerke.salonservice.entity.User;
import com.akerke.salonservice.entity.WorkTime;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AppointmentDTO(
        @NotNull
        Long treatmentId,
        @NotNull
        Long userId,
        @NotNull
        Long masterId,
        @NotNull
        Long workTimeId,
        @Size(min=2, max = 255)
        String note
) {
}
