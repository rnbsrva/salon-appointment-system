package com.akerke.salonservice.dto;

public record FeedbackDTO(
        Long userId,
        Long appointmentId,
        Integer rating,
        String feedbackText
) {
}
