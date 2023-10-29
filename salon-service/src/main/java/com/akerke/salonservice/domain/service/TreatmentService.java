package com.akerke.salonservice.domain.service;

import com.akerke.salonservice.domain.dto.TreatmentDTO;
import com.akerke.salonservice.domain.entity.Treatment;

import java.util.List;

/**
 * Service interface for managing treatments.
 */
public interface TreatmentService {

    /**
     * Saves a new treatment.
     *
     * @param treatmentDTO the DTO object containing the treatment details
     * @return the saved treatment entity
     */
    Treatment save(TreatmentDTO treatmentDTO);

    /**
     * Updates an existing treatment.
     *
     * @param treatmentDTO the DTO object containing the updated treatment details
     * @param id           the ID of the treatment to be updated
     */
    void update(TreatmentDTO treatmentDTO, Long id);

    /**
     * Deletes a treatment by its ID.
     *
     * @param id the ID of the treatment to be deleted
     */
    void delete(Long id);

    /**
     * Retrieves a treatment by its ID.
     *
     * @param id the ID of the treatment
     * @return the treatment entity
     */
    Treatment getById(Long id);

    /**
     * Retrieves all treatments.
     *
     * @return a list of all treatments
     */
    List<Treatment> getAll();

    /**
     * Retrieves a list of treatments by their IDs.
     *
     * @param ids the list of treatment IDs
     * @return a list of treatments
     */
    List<Treatment> getAll(List<Long> ids);
}