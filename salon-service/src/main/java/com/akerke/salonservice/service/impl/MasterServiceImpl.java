package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.entity.Master;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.MasterMapper;
import com.akerke.salonservice.repository.MasterRepository;
import com.akerke.salonservice.service.MasterService;
import com.akerke.salonservice.service.SalonService;
import com.akerke.salonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final MasterMapper masterMapper;
    private final UserService userService;
    private final SalonService salonService;

    @Override
    public Master save(MasterDTO masterDTO) {
        Master master = masterMapper.toModel(masterDTO);
        master.setUser(userService.getById(masterDTO.userId()));
        master.setSalon(salonService.getById(masterDTO.salonId()));
        return masterRepository.save(master);
    }

    @Override
    public void delete(Long id) {
        masterRepository.delete(this.getById(id));
    }

    @Override
    public void update(MasterDTO masterDTO, Long id) {
        var master = this.getById(id);
        masterMapper.update(masterDTO, master);
        masterRepository.save(master);
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
