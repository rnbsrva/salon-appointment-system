package com.akerke.salonservice.service;

import com.akerke.salonservice.entity.Salon;

import java.util.List;

public interface SalonService {

    Salon getSalon (Long id);

    List<Salon> getSalons ();

    void createSalon(SalonDTO salonDTO);

    void updateSalon(SalonDTO salonDTO, Long id);

    void delete(Long id);

}
