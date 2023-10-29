package com.akerke.salonservice.service;

import com.akerke.salonservice.common.constants.WeekDay;
import com.akerke.salonservice.domain.dto.WorkDayDTO;
import com.akerke.salonservice.domain.entity.Master;
import com.akerke.salonservice.domain.entity.Salon;
import com.akerke.salonservice.domain.entity.WorkDay;
import com.akerke.salonservice.domain.service.MasterService;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.mapper.WorkDayMapper;
import com.akerke.salonservice.domain.repository.WorkDayRepository;
import com.akerke.salonservice.domain.service.impl.WorkDayServiceImpl;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkDayServiceTest {

    @InjectMocks
    private WorkDayServiceImpl workDayService;

    @Mock
    private WorkDayRepository workDayRepository;
    @Mock
    private SalonService salonService;
    @Mock
    private MasterService masterService;
    @Mock
    private WorkDayMapper workDayMapper;

    private WorkDay workDay;
    private WorkDayDTO workDayDTO;

    @BeforeEach
    void setUp() {
        workDayDTO = new WorkDayDTO(
                1L, 1L, WeekDay.FRIDAY, new Date(), new Date(), false
        );

        var master = new Master();
        master.setId(1L);

        var salon = new Salon();
        salon.setId(1L);

        workDay = new WorkDay();
        workDay.setId(1L);
        workDay.setSalon(salon);
        workDay.setMaster(master);
    }

    @Test
    void delete_whenValidWorkDayId_thenDeleteWorkDay() {
        when(workDayRepository.findById(any(Long.class))).thenReturn(Optional.of(workDay));

        workDayService.delete(1L);

        verify(workDayRepository, times(1)).findById(workDay.getId());
        verify(workDayRepository, times(1)).delete(workDay);
    }

    @Test
    void delete_whenInvalidWorkDayId_thenThrowException() {
        when(workDayRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        var id = 1L;
        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> workDayService.delete(id));

        assertEquals("WorkDay with id: %d not found".formatted(id), entityNotFoundException.getMessage());
    }

    @Test
    void getById_whenValidWorkDayId_thenReturnWorkDay() {
        when(workDayRepository.findById(workDay.getId())).thenReturn(Optional.of(workDay));

        var returnedWorkDay = workDayService.getById(workDay.getId());

        assertEquals(returnedWorkDay, workDay);
    }

    @Test
    void getById_whenInvalidWorkDayId_thenThrowException() {
        when(workDayRepository.findById(workDay.getId())).thenReturn(Optional.empty());

        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> workDayService.getById(workDay.getId()));

        assertEquals("WorkDay with id: %d not found".formatted(workDay.getId()), entityNotFoundException.getMessage());
    }

    @Test
    void getAll_whenRepositoryReturnEmptyList_thenReturnEmptyList() {
        when(workDayRepository.findAll()).thenReturn(List.of());

        var workDays = workDayService.getAll();

        assertThat(workDays).hasSize(0);
    }

    @Test
    void getAll_whenRepositoryReturnList_thenReturnSameList() {
        var workDays = List.of(new WorkDay(), new WorkDay(), new WorkDay(), new WorkDay());

        when(workDayRepository.findAll()).thenReturn(workDays);

        var returnedWorkDays = workDayService.getAll();

        assertEquals(workDays, returnedWorkDays);
    }

    @Test
    void update_whenWorkDayExists_thenUpdateWorkDay() {
        Long id = 1L;
        when(workDayRepository.findById(id)).thenReturn(Optional.of(workDay));

        workDayService.update(workDayDTO, id);

        verify(workDayMapper, times(1)).update(workDayDTO, workDay);
        verify(workDayRepository, times(1)).save(workDay);
    }

    @Test
    void update_whenWorkDayDoesNotExist_thenThrowException() {
        Long id = 1L;
        when(workDayRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workDayService.update(workDayDTO, id));
        verify(workDayRepository, never()).save(any(WorkDay.class));
    }

    @Test
    void save() {
        when(workDayMapper.toModel(workDayDTO)).thenReturn(workDay);
        when(salonService.getById(workDayDTO.salonId())).thenReturn(workDay.getSalon());
        when(masterService.getById(workDayDTO.masterId())).thenReturn(workDay.getMaster());
        when(workDayRepository.save(workDay)).thenReturn(workDay);

        WorkDay savedWorkDay = workDayService.save(workDayDTO);

        assertThat(savedWorkDay).isEqualTo(workDay);

        verify(workDayMapper).toModel(workDayDTO);
        verify(salonService).getById(workDayDTO.salonId());
        verify(masterService).getById(workDayDTO.masterId());
        verify(workDayRepository).save(workDay);

    }
}