package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.AddressDTO;
import com.akerke.salonservice.entity.Address;

import java.util.List;

public interface AddressService {

    Address save (AddressDTO addressDTO);

    Address getById (Long id);

    List<Address> getAll ();

    void update (AddressDTO addressDTO, Long id);

    void delete (Long id);

}
