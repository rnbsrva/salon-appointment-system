package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.AppointmentDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.AppointmentMapper;
import com.akerke.salonservice.repository.AppointmentRepository;
import com.akerke.salonservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;


    @Override
    public void save(AppointmentDTO appointmentDTO) {

    }

    @Override
    public void update(AppointmentDTO appointmentDTO, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Appointment.class, id));
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }
}
