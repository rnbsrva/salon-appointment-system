package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.domain.dto.TreatmentDTO;
import com.akerke.salonservice.domain.entity.Treatment;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.repository.TreatmentRepository;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.domain.service.TreatmentService;
import com.akerke.salonservice.domain.mapper.TreatmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;
    private final SalonService salonService;


    @Override
    public Treatment save(TreatmentDTO treatmentDTO) {
        var treatment = treatmentMapper.toModel(treatmentDTO);
        var salon = salonService.getById(treatmentDTO.salonId());
        treatment.setSalon(salon);
        return treatmentRepository.save(treatment);
    }

    @Override
    public void update(TreatmentDTO treatmentDTO, Long id) {
        var treatment = this.getById(id);
        treatmentMapper.update(treatmentDTO, treatment);
        treatmentRepository.save(treatment);
    }

    @Override
    public void delete(Long id) {
        treatmentRepository.delete(this.getById(id));
    }

    @Override
    public Treatment getById(Long id) {
        return treatmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Treatment.class, id));
    }

    @Override
    public List<Treatment> getAll() {
        return treatmentRepository.findAll();
    }

    @Override
    public List<Treatment> getAll(List<Long> ids) {
        return treatmentRepository.findAllById(ids);
    }
}
