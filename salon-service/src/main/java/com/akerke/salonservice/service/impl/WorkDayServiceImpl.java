package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.WorkDayDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.WorkDay;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.WorkDayMapper;
import com.akerke.salonservice.repository.WorkDayRepository;
import com.akerke.salonservice.service.WorkDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkDayServiceImpl implements WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final WorkDayMapper workDayMapper;

    @Override
    public void save(WorkDayDTO workDayDTO) {

    }

    @Override
    public void update(WorkDayDTO workDayDTO, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public WorkDay getById(Long id) {
        return workDayRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(WorkDay.class, id));
    }

    @Override
    public List<WorkDay> getAll() {
        return workDayRepository.findAll();
    }
}
