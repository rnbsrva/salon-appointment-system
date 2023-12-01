package com.akerke.salonservice.domain.service;

import com.akerke.salonservice.domain.dto.MasterDTO;
import com.akerke.salonservice.domain.entity.Master;

import java.util.List;

/**
 * Service interface for managing masters.
 */
public interface MasterService {

    /**
     * Saves a new master.
     *
     * @param masterDTO the DTO object containing the master details
     * @return the saved master entity
     */
    Master save(MasterDTO masterDTO);

    /**
     * Deletes a master by its ID.
     *
     * @param id the ID of the master to be deleted
     */
    void delete(Long id);

    /**
     * Updates an existing master.
     *
     * @param masterDTO the DTO object containing the updated master details
     * @param id        the ID of the master to be updated
     */
    void update(MasterDTO masterDTO, Long id);

    /**
     * Retrieves a master by its ID.
     *
     * @param id the ID of the master
     * @return the master entity
     */
    Master getById(Long id);

    /**
     * Adds treatments to a master.
     *
     * @param id            the ID of the master
     * @param treatmentIds  the IDs of the treatments to be added
     */
    void addTreatment(Long id, List<Long> treatmentIds);

    /**
     * Retrieves all masters.
     *
     * @return a list of all masters
     */
    List<Master> getAll();

    void addImage(Long id, String imageId, Boolean isMainImage);
}