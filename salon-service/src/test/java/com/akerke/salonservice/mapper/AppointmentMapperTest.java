package com.akerke.salonservice.mapper;

import com.akerke.salonservice.constants.Status;
import com.akerke.salonservice.dto.AppointmentDTO;
import com.akerke.salonservice.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentMapperTest {

    private AppointmentMapper appointmentMapper;
    private AppointmentDTO appointmentDTO;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
        appointmentDTO = new AppointmentDTO(1L, 1L, 1L, 1L, "note");

        appointment = new Appointment();
        appointment.setStatus(Status.PENDING);
    }


    @Test
    void update_WhenAppointmentDtoIsNull_thenNoChangesAreMade() {
        assertDoesNotThrow(() -> appointmentMapper.update(null, appointment));
        assertEquals(Status.PENDING, appointment.getStatus());
    }

    @Test
    void update_WhenAppointmentIsNull_thenNullPointerExceptionIsThrown() {
        assertThrows(NullPointerException.class, () -> appointmentMapper.update(appointmentDTO, null));
    }

    @Test
    void toModel_whenValidAppointmentDTO_thenMapsProperties() {
        var treatmentId = 1L;
        var userId = 2L;
        var masterId = 3L;
        var workTimeId = 4L;
        var note = "Test note";

        var appointmentDTO = new AppointmentDTO(treatmentId, userId, masterId, workTimeId, note);

        var appointment = appointmentMapper.toModel(appointmentDTO);

        assertNotNull(appointment);
        assertEquals(Status.PENDING, appointment.getStatus());
        assertEquals(note, appointment.getNote());
    }

    @Test
    void toDTO_whenAppointmentProvided_thenReturnAppointmentDTO() {
        var user = new User();
        user.setId(1L);

        var master = new Master();
        master.setId(2L);

        var treatment = new Treatment();
        treatment.setId(3L);

        var workTime = new WorkTime();
        workTime.setId(4L);

        appointment.setUser(user);
        appointment.setMaster(master);
        appointment.setTreatment(treatment);
        appointment.setWorkTime(workTime);
        appointment.setNote("Test note");

        var appointmentDTO = appointmentMapper.toDTO(appointment);

        assertEquals(user.getId(), appointmentDTO.userId());
        assertEquals(master.getId(), appointmentDTO.masterId());
        assertEquals(treatment.getId(), appointmentDTO.treatmentId());
        assertEquals(workTime.getId(), appointmentDTO.workTimeId());
        assertEquals(appointment.getNote(), appointmentDTO.note());
    }
}