package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.Master;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.MasterMapper;
import com.akerke.salonservice.repository.MasterRepository;
import com.akerke.salonservice.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final MasterMapper masterMapper;

    @Override
    public void save(MasterDTO masterDTO) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(MasterDTO masterDTO, Long id) {

    }

    @Override
    public Master getById(Long id) {
        return masterRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Master.class, id));
    }

    @Override
    public List<Master> getAll() {
        return masterRepository.findAll();
    }
}
