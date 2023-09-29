package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.WorkDayDTO;
import com.akerke.salonservice.entity.WorkDay;

import java.util.List;

public interface WorkDayService {

    void save(WorkDayDTO workDayDTO);

    void update (WorkDayDTO workDayDTO, Long id);

    void delete (Long id);

    WorkDay getById(Long id);

    List<WorkDay> getAll();

}
