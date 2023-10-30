package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.domain.dto.AppointmentDTO;
import com.akerke.salonservice.domain.entity.Appointment;
import com.akerke.salonservice.domain.repository.AppointmentRepository;
import com.akerke.salonservice.domain.service.*;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserService userService;
    private final MasterService masterService;
    private final TreatmentService treatmentService;
    private final WorkTimeService workTimeService;


    @Override
    public Appointment save(AppointmentDTO appointmentDTO) {
        var appointment = appointmentMapper.toModel(appointmentDTO);
        appointment.setUser(userService.getById(appointmentDTO.userId()));
        appointment.setMaster(masterService.getById(appointmentDTO.masterId()));
        appointment.setTreatment(treatmentService.getById(appointmentDTO.treatmentId()));
        appointment.setWorkTime(workTimeService.getById(appointmentDTO.workTimeId()) );
        return appointmentRepository.save(appointment);
    }

    @Override
    public void update(AppointmentDTO appointmentDTO, Long id) {
        var appointment = this.getById(id);
        appointmentMapper.update(appointmentDTO, appointment);
        appointmentRepository.save(appointment);
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.delete(this.getById(id));
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