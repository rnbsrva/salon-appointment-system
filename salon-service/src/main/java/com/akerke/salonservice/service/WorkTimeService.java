package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.WorkTimeDTO;
import com.akerke.salonservice.entity.WorkTime;

import java.util.List;

public interface WorkTimeService {

    void save(WorkTimeDTO workTimeDTO);

    void delete(Long id);

    WorkTime getById(Long id);

    List<WorkTime> getAll();

    void update(WorkTimeDTO workTimeDTO, Long id);
}
