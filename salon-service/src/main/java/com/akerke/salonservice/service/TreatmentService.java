package com.akerke.salonservice.service;

import com.akerke.salonservice.entity.Treatment;

import java.util.List;
import java.util.concurrent.TransferQueue;

public interface TreatmentService {

    void save (TreatmentDTO treatmentDTO);

    void update (TreatmentDTO treatmentDTO, Long id);

    void delete(Long id);

    Treatment getById(Long id);

    List<Treatment> getAll();
}
