package com.akerke.salonservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackDTO(
        @NotNull
        Long userId,
        @NotNull
        Long appointmentId,
        @NotNull
        Integer rating,
        @Size(min=2, max = 255)
        String feedbackText
) {
}
