package com.akerke.salonservice.domain.service;

/**
 * Service interface for managing managers.
 */
public interface ManagerService {

    /**
     * Adds a manager to a salon.
     *
     * @param userId  the ID of the user to be added as a manager
     * @param salonId the ID of the salon to which the user will be added as a manager
     */
    void add(Long userId, Long salonId);

    /**
     * Removes a manager from a salon.
     *
     * @param userId  the ID of the user to be removed as a manager
     * @param salonId the ID of the salon from which the user will be removed as a manager
     */
    void remove(Long userId, Long salonId);

}