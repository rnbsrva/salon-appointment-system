package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.common.exception.InvalidRequestPayloadException;
import com.akerke.salonservice.domain.dto.FeedbackDTO;
import com.akerke.salonservice.domain.entity.Appointment;
import com.akerke.salonservice.domain.entity.Feedback;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.repository.FeedbackRepository;
import com.akerke.salonservice.domain.service.AppointmentService;
import com.akerke.salonservice.domain.service.FeedbackService;
import com.akerke.salonservice.domain.mapper.FeedbackMapper;
import com.akerke.salonservice.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final UserService userService;
    private final AppointmentService appointmentService;


    @Override
    public Feedback save(FeedbackDTO feedbackDTO) {
        var appointment = appointmentService.getById(feedbackDTO.appointmentId());
        var feedback = feedbackMapper.toModel(feedbackDTO);

        if(isFeedbackWritten(appointment)){
            throw new InvalidRequestPayloadException("Feedback for appointment with id %s already exists".formatted(feedbackDTO.appointmentId().toString()));
        }

        if(feedbackDTO.feedbackText()==null) {
            feedback.setFeedbackText("");
        }
        feedback.setMaster(appointment.getMaster());
        feedback.setUser(appointment.getUser());
        feedback.setAppointment(appointment);


        return feedbackRepository.save(feedback);
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

    private Boolean isFeedbackWritten (Appointment appointment) {
        return feedbackRepository.findByAppointment(appointment).isPresent();
    }
}
