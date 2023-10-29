package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.FeedbackDTO;
import com.akerke.salonservice.entity.Feedback;

import java.util.List;

/**
 * Service interface for managing feedback.
 */
public interface FeedbackService {

    /**
     * Saves a new feedback.
     *
     * @param feedbackDTO the DTO object containing the feedback details
     * @return the saved feedback entity
     */
    Feedback save(FeedbackDTO feedbackDTO);

    /**
     * Updates an existing feedback.
     *
     * @param feedbackDTO the DTO object containing the updated feedback details
     * @param id          the ID of the feedback to be updated
     */
    void update(FeedbackDTO feedbackDTO, Long id);

    /**
     * Deletes a feedback by its ID.
     *
     * @param id the ID of the feedback to be deleted
     */
    void delete(Long id);

    /**
     * Retrieves a feedback by its ID.
     *
     * @param id the ID of the feedback
     * @return the feedback entity
     */
    Feedback getById(Long id);

    /**
     * Retrieves all feedbacks.
     *
     * @return a list of all feedbacks
     */
    List<Feedback> getAll();

}