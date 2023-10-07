package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.WorkDayDTO;
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
    public WorkDay save(WorkDayDTO workDayDTO) {
        return workDayRepository.save(workDayMapper.toModel(workDayDTO));
    }

    @Override
    public void update(WorkDayDTO workDayDTO, Long id) {
        var workDay = this.getById(id);
        workDayMapper.update(workDayDTO, workDay);
        workDayRepository.save(workDay);
    }

    @Override
    public void delete(Long id) {
        workDayRepository.delete(this.getById(id));
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
