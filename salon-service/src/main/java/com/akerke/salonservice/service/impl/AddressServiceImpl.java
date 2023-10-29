package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.AddressDTO;
import com.akerke.salonservice.entity.Address;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.AddressMapper;
import com.akerke.salonservice.repository.AddressRepository;
import com.akerke.salonservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Override
    public Address save(AddressDTO addressDTO) {
        return addressRepository.save(addressMapper.toModel(addressDTO));
    }

    @Override
    public Address getById(Long id) {
        return addressRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Address.class, id));
    }

    @Override
    public void update(AddressDTO addressDTO, Long id) {
        var address = this.getById(id);
        addressMapper.update(addressDTO, address);
        addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        addressRepository.delete(this.getById(id));
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

}
