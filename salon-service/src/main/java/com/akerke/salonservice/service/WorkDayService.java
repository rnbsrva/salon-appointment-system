package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.WorkDayDTO;
import com.akerke.salonservice.entity.WorkDay;

import java.util.List;

public interface WorkDayService {

    /**
     * Saves a new work day.
     *
     * @param workDayDTO the DTO object containing the work day details
     * @return the saved work day entity
     */
    WorkDay save(WorkDayDTO workDayDTO);

    /**
     * Updates an existing work day.
     *
     * @param workDayDTO the DTO object containing the updated work day details
     * @param id         the ID of the work day to update
     */
    void update(WorkDayDTO workDayDTO, Long id);

    /**
     * Deletes a work day by ID.
     *
     * @param id the ID of the work day to delete
     */
    void delete(Long id);

    /**
     * Retrieves a work day by ID.
     *
     * @param id the ID of the work day to retrieve
     * @return the work day entity
     */
    WorkDay getById(Long id);

    /**
     * Retrieves all work days.
     *
     * @return a list of all work day entities
     */
    List<WorkDay> getAll();

}