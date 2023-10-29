package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.WorkTimeDTO;
import com.akerke.salonservice.entity.WorkTime;

import java.util.List;

public interface WorkTimeService {

    /**
     * Saves a new work time.
     *
     * @param workTimeDTO the DTO object containing the work time details
     * @return the saved work time entity
     */
    WorkTime save(WorkTimeDTO workTimeDTO);

    /**
     * Deletes a work time by ID.
     *
     * @param id the ID of the work time to delete
     */
    void delete(Long id);

    /**
     * Retrieves a work time by ID.
     *
     * @param id the ID of the work time to retrieve
     * @return the work time entity
     */
    WorkTime getById(Long id);

    /**
     * Retrieves all work times.
     *
     * @return a list of all work time entities
     */
    List<WorkTime> getAll();

    /**
     * Updates an existing work time.
     *
     * @param workTimeDTO the DTO object containing the updated work time details
     * @param id          the ID of the work time to update
     */
    void update(WorkTimeDTO workTimeDTO, Long id);
}