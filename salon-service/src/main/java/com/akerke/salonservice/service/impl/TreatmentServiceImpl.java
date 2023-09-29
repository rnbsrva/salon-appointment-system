package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.TreatmentDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.Treatment;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.TreatmentMapper;
import com.akerke.salonservice.repository.TreatmentRepository;
import com.akerke.salonservice.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;


    @Override
    public void save(TreatmentDTO treatmentDTO) {

    }

    @Override
    public void update(TreatmentDTO treatmentDTO, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Treatment getById(Long id) {
        return treatmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Treatment.class, id));
    }

    @Override
    public List<Treatment> getAll() {
        return treatmentRepository.findAll();
    }
}
