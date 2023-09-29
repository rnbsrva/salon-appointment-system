package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.WorkTimeDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.WorkTime;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.WorkTimeMapper;
import com.akerke.salonservice.repository.WorkTimeRepository;
import com.akerke.salonservice.service.WorkTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkTimeServiceImpl implements WorkTimeService {

    private final WorkTimeRepository workTimeRepository;
    private final WorkTimeMapper workTimeMapper;

    @Override
    public void save(WorkTimeDTO workTimeDTO) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public WorkTime getById(Long id) {
        return workTimeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(WorkTime.class, id));
    }

    @Override
    public List<WorkTime> getAll() {
        return workTimeRepository.findAll();
    }

    @Override
    public void update(WorkTimeDTO workTimeDTO, Long id) {

    }
}