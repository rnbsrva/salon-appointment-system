package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.FeedbackDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.Feedback;
import com.akerke.salonservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FeedbackMapperTest {

    private FeedbackMapper feedbackMapper;
    private Feedback feedback;
    private FeedbackDTO feedbackDTO;

    @BeforeEach
    void setup() {
        feedbackMapper = Mappers.getMapper(FeedbackMapper.class);

        feedback = new Feedback();
        feedback.setRating(3);
        feedback.setFeedbackText("feedback");

        var user = new User();
        user.setId(1L);

        var appointment = new Appointment();
        appointment.setId(2L);

        feedback.setUser(user);
        feedback.setAppointment(appointment);

        feedbackDTO = new FeedbackDTO(1L, 1L, 5, "New feedback");
    }

    @Test
    void update_whenFeedbackEntityIsUpdated_thenSuccess() {
        feedbackMapper.update(feedbackDTO, feedback);

        assertEquals(feedbackDTO.rating(), feedback.getRating());
        assertEquals(feedbackDTO.feedbackText(), feedback.getFeedbackText());
    }

    @Test
    void toModel_whenNullFeedbackDTO_whenReturnNull() {
        var feedback = feedbackMapper.toModel(null);

        assertNull(feedback);
    }

    @Test
    void toDTO_whenFeedbackNotNull_thenReturnFeedbackDTO() {
        var feedbackDTO = feedbackMapper.toDTO(feedback);

        assertEquals(feedback.getUser().getId(), feedbackDTO.userId());
        assertEquals(feedback.getAppointment().getId(), feedbackDTO.appointmentId());
        assertEquals(feedback.getRating(), feedbackDTO.rating());
        assertEquals(feedback.getFeedbackText(), feedbackDTO.feedbackText());
    }

    @Test
    void toDTO_whenFeedbackNull_thenReturnNull() {
        var feedbackDTO = feedbackMapper.toDTO(null);
        assertNull(feedbackDTO);
    }
}

