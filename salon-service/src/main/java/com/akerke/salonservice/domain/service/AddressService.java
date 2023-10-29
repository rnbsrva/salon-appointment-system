package com.akerke.salonservice.domain.service;

import com.akerke.salonservice.domain.dto.AddressDTO;
import com.akerke.salonservice.domain.entity.Address;

import java.util.List;

/**
 * Service interface for managing addresses.
 */
public interface AddressService {

    /**
     * Saves a new address.
     *
     * @param addressDTO the DTO object containing the address details
     * @return the saved address entity
     */
    Address save(AddressDTO addressDTO);

    /**
     * Retrieves an address by its ID.
     *
     * @param id the ID of the address
     * @return the address entity
     */
    Address getById(Long id);

    /**
     * Retrieves all addresses.
     *
     * @return a list of all addresses
     */
    List<Address> getAll();

    /**
     * Updates an existing address.
     *
     * @param addressDTO the DTO object containing the updated address details
     * @param id         the ID of the address to be updated
     */
    void update(AddressDTO addressDTO, Long id);

    /**
     * Deletes an address by its ID.
     *
     * @param id the ID of the address to be deleted
     */
    void delete(Long id);

}