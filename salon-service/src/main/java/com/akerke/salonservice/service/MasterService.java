package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.entity.Master;

import java.util.List;

public interface MasterService {

    Master save (MasterDTO masterDTO);

    void delete (Long id);

    void update (MasterDTO masterDTO, Long id);

    Master getById(Long id);

    void addTreatment(Long id, List<Long> treatmentIds);

    List<Master> getAll();

}
