package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.TreatmentDTO;
import com.akerke.salonservice.entity.Treatment;

import java.util.List;

public interface TreatmentService {

    Treatment save (TreatmentDTO treatmentDTO);

    void update (TreatmentDTO treatmentDTO, Long id);

    void delete(Long id);

    Treatment getById(Long id);

    List<Treatment> getAll();
}
