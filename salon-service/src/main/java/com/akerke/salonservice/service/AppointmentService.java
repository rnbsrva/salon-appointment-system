package com.akerke.salonservice.service;

import com.akerke.salonservice.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    void save (AppointmentDTO appointmentDTO);

    void update (AppointmentDTO appointmentDTO, Long id);

    void delete (Long id);

    Appointment getById(Long id);

    List<Appointment> getAll();

}
