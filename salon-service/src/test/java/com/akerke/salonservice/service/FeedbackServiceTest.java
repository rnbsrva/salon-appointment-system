package com.akerke.salonservice.service;

import com.akerke.salonservice.domain.dto.FeedbackDTO;
import com.akerke.salonservice.domain.entity.Feedback;
import com.akerke.exceptionlib.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.mapper.FeedbackMapper;
import com.akerke.salonservice.domain.repository.FeedbackRepository;
import com.akerke.salonservice.domain.service.impl.FeedbackServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private FeedbackMapper feedbackMapper;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    private FeedbackDTO feedbackDTO;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        feedbackDTO = new FeedbackDTO(1L, 1L, 2, "Feedback");
        feedback = new Feedback();
        feedback.setId(1L);

    }

    @Test
    void delete_whenValidFeedbackId_thenDeleteFeedback() {
        when(feedbackRepository.findById(any(Long.class))).thenReturn(Optional.of(feedback));

        feedbackService.delete(1L);

        verify(feedbackRepository, times(1)).findById(feedback.getId());
        verify(feedbackRepository, times(1)).delete(feedback);
    }

    @Test
    void delete_whenInvalidFeedbackId_thenThrowException() {
        when(feedbackRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        var id = 1L;
        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> feedbackService.delete(id));

        assertEquals("Feedback with id: %d not found".formatted(id), entityNotFoundException.getMessage());
    }

    @Test
    void getById_whenValidFeedbackId_thenReturnFeedback() {
        when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));

        var returnedFeedback = feedbackService.getById(feedback.getId());

        assertEquals(returnedFeedback, feedback);
    }

    @Test
    void getById_whenInvalidFeedbackId_thenThrowException() {
        when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.empty());

        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> feedbackService.getById(feedback.getId()));

        assertEquals("Feedback with id: %d not found".formatted(feedback.getId()), entityNotFoundException.getMessage());
    }

    @Test
    void getAll_whenRepositoryReturnEmptyList_thenReturnEmptyList() {
        when(feedbackRepository.findAll()).thenReturn(List.of());

        var feedbacks = feedbackService.getAll();

        assertThat(feedbacks).hasSize(0);
    }

    @Test
    void getAll_whenRepositoryReturnList_thenReturnSameList() {
        var feedbacks = List.of(new Feedback(), new Feedback(), new Feedback(), new Feedback());

        when(feedbackRepository.findAll()).thenReturn(feedbacks);

        var returnedFeedbacks = feedbackService.getAll();

        assertEquals(feedbacks, returnedFeedbacks);
    }

    @Test
    void save_whenValidDTO_thenSaveReturnSavedFeedback() {
        when(feedbackMapper.toModel(feedbackDTO)).thenReturn(feedback);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        var savedFeedback = feedbackService.save(feedbackDTO);

        assertEquals(savedFeedback, feedback);
        verify(feedbackRepository).save(feedback);
    }

    @Test
    void save_whenNullDTO_thenReturnNull() {
        var savedFeedback = feedbackService.save(null);

        assertNull(savedFeedback);
    }

    @Test
    void update_whenFeedbackExists_thenUpdateFeedback() {
        Long id = 1L;
        when(feedbackRepository.findById(id)).thenReturn(Optional.of(feedback));

        feedbackService.update(feedbackDTO, id);

        verify(feedbackMapper, times(1)).update(feedbackDTO, feedback);
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void update_whenFeedbackDoesNotExist_thenThrowException() {
        Long id = 1L;
        when(feedbackRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> feedbackService.update(feedbackDTO, id));
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }


}