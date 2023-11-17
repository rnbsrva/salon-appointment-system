package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.domain.dto.FeedbackDTO;
import com.akerke.salonservice.domain.entity.Feedback;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.repository.FeedbackRepository;
import com.akerke.salonservice.domain.service.FeedbackService;
import com.akerke.salonservice.domain.mapper.FeedbackMapper;
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
