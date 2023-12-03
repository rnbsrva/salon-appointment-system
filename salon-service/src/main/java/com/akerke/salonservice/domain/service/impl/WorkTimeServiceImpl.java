package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.common.exception.InvalidRequestPayloadException;
import com.akerke.salonservice.domain.dto.WorkTimeDTO;
import com.akerke.salonservice.domain.entity.WorkTime;
import com.akerke.salonservice.domain.repository.WorkTimeRepository;
import com.akerke.salonservice.domain.service.WorkDayService;
import com.akerke.salonservice.domain.service.WorkTimeService;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.mapper.WorkTimeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkTimeServiceImpl implements WorkTimeService {

    private final WorkTimeRepository workTimeRepository;
    private final WorkTimeMapper workTimeMapper;
    private final WorkDayService workDayService;

    @Override
    public WorkTime save(WorkTimeDTO workTimeDTO) {
        var workDay = workDayService.getById(workTimeDTO.workDayId());

        if(workDay.getIsHoliday()){
            throw new InvalidRequestPayloadException("Work time cannot be created at holidays");
        }

        var workTime = workTimeMapper.toModel(workTimeDTO);
        workTime.setWorkDay(workDay);
        return workTimeRepository.save(workTime);
    }

    @Override
    public void update(WorkTimeDTO workTimeDTO, Long id) {
        var workTime = this.getById(id);
        workTimeMapper.update(workTimeDTO, workTime);
        workTimeRepository.save(workTime);
    }

    @Override
    public void delete(Long id) {
        workTimeRepository.delete(this.getById(id));
    }

    @Override
    public WorkTime getById(Long id) {
        return workTimeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(WorkTime.class, id));
    }

    @Override
    public List<WorkTime> getAll() {
        return workTimeRepository.findAll();
    }


}
