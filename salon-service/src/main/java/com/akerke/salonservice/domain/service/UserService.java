package com.akerke.salonservice.domain.service;

import com.akerke.salonservice.domain.dto.UserDTO;
import com.akerke.salonservice.domain.entity.User;

import java.util.List;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Saves a new user.
     *
     * @param userDTO the DTO object containing the user details
     * @return the saved user entity
     */
    User save(UserDTO userDTO);

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user
     * @return the user entity
     */
    User getById(Long id);

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    List<User> getAll();

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to be deleted
     */
    void delete(Long id);

    /**
     * Updates an existing user.
     *
     * @param userDTO the DTO object containing the updated user details
     * @param id      the ID of the user to be updated
     */
    void update(UserDTO userDTO, Long id);

}