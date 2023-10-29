package com.akerke.salonservice.service;

import com.akerke.salonservice.domain.dto.WorkTimeDTO;
import com.akerke.salonservice.domain.entity.WorkDay;
import com.akerke.salonservice.domain.entity.WorkTime;
import com.akerke.salonservice.domain.service.WorkDayService;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.mapper.WorkTimeMapper;
import com.akerke.salonservice.domain.repository.WorkTimeRepository;
import com.akerke.salonservice.domain.service.impl.WorkTimeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkTimeServiceTest {

    @InjectMocks
    private WorkTimeServiceImpl workTimeService;
    @Mock
    private WorkTimeRepository workTimeRepository;
    @Mock
    private WorkTimeMapper workTimeMapper;
    @Mock
    private WorkDayService workDayService;

    private WorkTime workTime;
    private WorkTimeDTO workTimeDTO;

    @BeforeEach
    void setUp() {
        workTime = new WorkTime();
        workTime.setId(1L);

        workTimeDTO = new WorkTimeDTO(
                new Date(), new Date(), false, 1L
        );

        var workDay = new WorkDay();
        workDay.setId(1L);

        workTime.setWorkDay(workDay);
    }

    @Test
    void delete_whenValidWorkTimeId_thenDeleteUser() {
        when(workTimeRepository.findById(any(Long.class))).thenReturn(Optional.of(workTime));

        workTimeService.delete(1L);

        verify(workTimeRepository, times(1)).findById(workTime.getId());
        verify(workTimeRepository, times(1)).delete(workTime);
    }

    @Test
    void delete_whenInvalidWorkTimeId_thenThrowException() {
        when(workTimeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        var id = 1L;
        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> workTimeService.delete(id));

        assertEquals("WorkTime with id: %d not found".formatted(id), entityNotFoundException.getMessage());
    }

    @Test
    void getById_whenValidWorkTimeId_thenReturnUser() {
        when(workTimeRepository.findById(workTime.getId())).thenReturn(Optional.of(workTime));

        var returnedWorkTime = workTimeService.getById(workTime.getId());

        assertEquals(returnedWorkTime, workTime);
    }

    @Test
    void getById_whenInvalidWorkTimeId_thenThrowException() {
        when(workTimeRepository.findById(workTime.getId())).thenReturn(Optional.empty());

        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> workTimeService.getById(workTime.getId()));

        assertEquals("WorkTime with id: %d not found".formatted(workTime.getId()), entityNotFoundException.getMessage());
    }

    @Test
    void getAll_whenRepositoryReturnEmptyList_thenReturnEmptyList() {
        when(workTimeRepository.findAll()).thenReturn(List.of());

        var workTimes = workTimeService.getAll();

        assertThat(workTimes).hasSize(0);
    }

    @Test
    void getAll_whenRepositoryReturnList_thenReturnSameList() {
        var workTimes = List.of(new WorkTime(), new WorkTime(), new WorkTime(), new WorkTime());

        when(workTimeRepository.findAll()).thenReturn(workTimes);

        var returnedWorkTimes = workTimeService.getAll();

        assertEquals(workTimes, returnedWorkTimes);
    }

    @Test
    void update_whenInvalidWorkTimeId_thenThrowException() {
        when(workTimeRepository.findById(workTime.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workTimeService.update(workTimeDTO, workTime.getId()));
    }

    @Test
    void update_whenValidId_thenMakeChanges() {
        when(workTimeRepository.findById(workTime.getId())).thenReturn(Optional.of(workTime));

        workTimeService.update(workTimeDTO, workTime.getId());

        verify(workTimeRepository).findById(workTime.getId());
        verify(workTimeMapper).update(workTimeDTO, workTime);
        verify(workTimeRepository).save(workTime);
    }

    @Test
    void save() {
        when(workTimeMapper.toModel(workTimeDTO)).thenReturn(workTime);
        when(workDayService.getById(workTimeDTO.workDayId())).thenReturn(workTime.getWorkDay());
        when(workTimeRepository.save(workTime)).thenReturn(workTime);

        var savedWorkTime = workTimeService.save(workTimeDTO);

        assertThat(savedWorkTime).isEqualTo(workTime);

        verify(workTimeMapper).toModel(workTimeDTO);
        verify(workDayService).getById(workTimeDTO.workDayId());
        verify(workTimeRepository).save(workTime);
    }
}