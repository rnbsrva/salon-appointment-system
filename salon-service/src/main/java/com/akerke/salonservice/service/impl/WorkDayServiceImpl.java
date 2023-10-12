package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.constants.WeekDay;
import com.akerke.salonservice.dto.WorkDayDTO;
import com.akerke.salonservice.entity.WorkDay;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.WorkDayMapper;
import com.akerke.salonservice.repository.WorkDayRepository;
import com.akerke.salonservice.service.MasterService;
import com.akerke.salonservice.service.SalonService;
import com.akerke.salonservice.service.WorkDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkDayServiceImpl implements WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final SalonService salonService;
    private final MasterService masterService;
    private final WorkDayMapper workDayMapper;

    @Override
    public WorkDay save(WorkDayDTO workDayDTO) {
        var workDay = workDayMapper.toModel(workDayDTO);
        var salon = salonService.getById(workDayDTO.salonId());
        var master = masterService.getById(workDayDTO.masterId());
        workDay.setMaster(master);
        workDay.setSalon(salon);
        return workDayRepository.save(workDay);
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
