package com.akerke.salonservice.service;

import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.dto.AppointmentDTO;
import com.akerke.salonservice.domain.entity.Appointment;
import com.akerke.salonservice.domain.service.UserService;
import com.akerke.salonservice.domain.mapper.AppointmentMapper;
import com.akerke.salonservice.domain.repository.AppointmentRepository;
import com.akerke.salonservice.domain.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private AppointmentMapper appointmentMapper;

    private AppointmentDTO appointmentDTO;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointmentDTO = new AppointmentDTO(1L, 1L, 1L, 1L, "Test note");
        appointment = new Appointment();
        appointment.setId(1L);
    }


    @Test
    void delete_whenValidAppointmentId_thenDeleteAppointment() {
        when(appointmentRepository.findById(any(Long.class))).thenReturn(Optional.of(appointment));

        appointmentService.delete(appointment.getId());

        verify(appointmentRepository, times(1)).findById(appointment.getId());
        verify(appointmentRepository, times(1)).delete(appointment);
    }


    @Test
    void getById_whenValidAppointmentId_thenReturnAppointment() {
        when(appointmentRepository.findById(any(Long.class))).thenReturn(Optional.of(appointment));

        Appointment foundAppointment = appointmentService.getById(appointment.getId());

        verify(appointmentRepository, times(1)).findById(appointment.getId());
        assertThat(foundAppointment).isNotNull();
        assertThat(foundAppointment.getId()).isEqualTo(appointment.getId());
    }

    @Test
    void getById_whenInvalidAppointmentId_thenReturnNull() {
        when(appointmentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.getById(999L));

        verify(appointmentRepository, times(1)).findById(999L);
    }

    @Test
    void updateWhenValidAppointmentDTOAndId_thenUpdateAppointment() {
        when(appointmentRepository.findById(any(Long.class))).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        appointmentService.update(appointmentDTO, appointment.getId());

        verify(appointmentRepository, times(1)).findById(appointment.getId());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void update_WhenInvalidId_thenThrowException() {
        when(appointmentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.update(appointmentDTO, 999L))
                .isInstanceOf(RuntimeException.class);

        verify(appointmentRepository, times(1)).findById(999L);
        verify(appointmentRepository, times(0)).save(any(Appointment.class));
    }

    @Test
    void getAll_whenAppointmentsExist_thenReturnAppointments() {
        var appointments = Collections.singletonList(appointment);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        var returnedAppointments = appointmentService.getAll();

        verify(appointmentRepository, times(1)).findAll();
        assertThat(returnedAppointments).isNotNull();
        assertThat(returnedAppointments).isEqualTo(appointments);
    }

    @Test
    void getAll_whenNoAppointments_thenReturnEmptyList() {
        when(appointmentRepository.findAll()).thenReturn(Collections.emptyList());

        var returnedAppointments = appointmentService.getAll();

        verify(appointmentRepository, times(1)).findAll();
        assertThat(returnedAppointments).isNotNull();
        assertThat(returnedAppointments).isEmpty();
    }

    @Test
    void update_whenAppointmentExists_thenUpdateAppointment() {
        Long id = 1L;
        var appointment = new Appointment();
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));

        appointmentService.update(appointmentDTO, id);

        verify(appointmentMapper, times(1)).update(appointmentDTO, appointment);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void update_whenAppointmentDoesNotExist_thenThrowException() {
        Long id = 1L;
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.update(appointmentDTO, id));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }


}