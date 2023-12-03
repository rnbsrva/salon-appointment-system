package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.common.exception.InvalidRequestPayloadException;
import com.akerke.salonservice.domain.dto.WorkDayDTO;
import com.akerke.salonservice.domain.entity.WorkDay;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.repository.WorkDayRepository;
import com.akerke.salonservice.domain.service.MasterService;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.domain.service.WorkDayService;
import com.akerke.salonservice.domain.mapper.WorkDayMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        if(!Objects.equals(master.getSalon().getId(), salon.getId())){
            throw new InvalidRequestPayloadException("This master does not belong to salon");
        }
        workDay.setMaster(master);
        workDay.setSalon(salon);
        return workDayRepository.save(workDay);
    }

    @Override
    public void update(WorkDayDTO workDayDTO, Long id) {
        var workDay = this.getById(id);
        var master = masterService.getById(workDayDTO.masterId());
        var salon = salonService.getById(workDayDTO.salonId());

        if(!Objects.equals(master.getSalon().getId(), salon.getId())){
            throw new InvalidRequestPayloadException("This master does not belong to salon");
        }

        workDayMapper.update(workDayDTO, workDay);
        workDayRepository.save(workDay);
    }

    @Override
    public void delete(Long id) {
        workDayRepository.delete(this.getById(id));
    }

    @Override
    public WorkDay getById(Long id) {
        return workDayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(WorkDay.class, id));
    }

    @Override
    public List<WorkDay> getAll() {
        return workDayRepository.findAll();
    }
}
