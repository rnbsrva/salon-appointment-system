package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.AppointmentDTO;
import com.akerke.salonservice.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment save (AppointmentDTO appointmentDTO);

    void update (AppointmentDTO appointmentDTO, Long id);

    void delete (Long id);

    Appointment getById(Long id);

    List<Appointment> getAll();

}
