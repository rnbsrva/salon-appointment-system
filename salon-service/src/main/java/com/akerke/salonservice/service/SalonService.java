package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.SalonDTO;
import com.akerke.salonservice.entity.Salon;

import java.util.List;

/**
 * Service interface for managing salons.
 */
public interface SalonService {

    /**
     * Retrieves a salon by its ID.
     *
     * @param id the ID of the salon
     * @return the salon entity
     */
    Salon getById(Long id);

    /**
     * Retrieves all salons.
     *
     * @return a list of all salons
     */
    List<Salon> getAll();

    /**
     * Saves a new salon.
     *
     * @param salonDTO the DTO object containing the salon details
     * @return the saved salon entity
     */
    Salon save(SalonDTO salonDTO);

    /**
     * Updates an existing salon.
     *
     * @param salonDTO the DTO object containing the updated salon details
     * @param id       the ID of the salon to be updated
     */
    void update(SalonDTO salonDTO, Long id);

    /**
     * Deletes a salon by its ID.
     *
     * @param id the ID of the salon to be deleted
     */
    void delete(Long id);

}