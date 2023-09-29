package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.SalonDTO;
import com.akerke.salonservice.entity.Salon;

import java.util.List;

public interface SalonService {

    Salon getById (Long id);

    List<Salon> getAll ();

    void save(SalonDTO salonDTO);

    void update(SalonDTO salonDTO, Long id);

    void delete(Long id);

}
