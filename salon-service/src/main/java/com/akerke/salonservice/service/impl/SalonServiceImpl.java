package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.SalonDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.SalonMapper;
import com.akerke.salonservice.repository.SalonRepository;
import com.akerke.salonservice.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;
    private final SalonMapper salonMapper;

    @Override
    public Salon getById(Long id) {
        return salonRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Salon.class, id));
    }

    @Override
    public List<Salon> getAll() {
        return salonRepository.findAll();
    }

    @Override
    public void save(SalonDTO salonDTO) {

    }

    @Override
    public void update(SalonDTO salonDTO, Long id) {

    }

    @Override
    public void delete(Long id) {

    }
}
