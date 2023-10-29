package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.AppointmentDTO;
import com.akerke.salonservice.entity.Appointment;

import java.util.List;

/**
 * Service interface for managing appointments.
 */
public interface AppointmentService {

    /**
     * Saves a new appointment.
     *
     * @param appointmentDTO the DTO object containing the appointment details
     * @return the saved appointment entity
     */
    Appointment save(AppointmentDTO appointmentDTO);

    /**
     * Updates an existing appointment.
     *
     * @param appointmentDTO the DTO object containing the updated appointment details
     * @param id             the ID of the appointment to be updated
     */
    void update(AppointmentDTO appointmentDTO, Long id);

    /**
     * Deletes an appointment by its ID.
     *
     * @param id the ID of the appointment to be deleted
     */
    void delete(Long id);

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id the ID of the appointment
     * @return the appointment entity
     */
    Appointment getById(Long id);

    /**
     * Retrieves all appointments.
     *
     * @return a list of all appointments
     */
    List<Appointment> getAll();

}