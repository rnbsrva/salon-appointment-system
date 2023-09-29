package com.akerke.salonservice.dto;

import com.akerke.salonservice.constants.Status;
import com.akerke.salonservice.entity.Master;
import com.akerke.salonservice.entity.Treatment;
import com.akerke.salonservice.entity.User;
import com.akerke.salonservice.entity.WorkTime;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public record AppointmentDTO(
        Long treatmentId,
        Long userId,
        Long masterId,
        Long workTimeId,
        String note
) {
}
