package com.akerke.salonservice.service;

import com.akerke.salonservice.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    void save (FeedbackDTO feedbackDTO);

    void update (FeedbackDTO feedbackDTO, Long id);

    void delete(Long id);

    Feedback getFeedback(Long id);

    List<Feedback> getAll();

}
