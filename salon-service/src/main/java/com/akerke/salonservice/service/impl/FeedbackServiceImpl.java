package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.FeedbackDTO;
import com.akerke.salonservice.entity.Feedback;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.FeedbackMapper;
import com.akerke.salonservice.repository.FeedbackRepository;
import com.akerke.salonservice.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;


    @Override
    public Feedback save(FeedbackDTO feedbackDTO) {
        return feedbackRepository.save(feedbackMapper.toModel(feedbackDTO));
    }

    @Override
    public void update(FeedbackDTO feedbackDTO, Long id) {
        var feedback = this.getById(id);
        feedbackMapper.update(feedbackDTO, feedback);
        feedbackRepository.save(feedback);
    }

    @Override
    public void delete(Long id) {
        feedbackRepository.delete(this.getById(id));
    }

    @Override
    public Feedback getById(Long id) {
        return feedbackRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Feedback.class, id));
    }

    @Override
    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }
}
