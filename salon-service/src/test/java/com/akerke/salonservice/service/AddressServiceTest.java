package com.akerke.salonservice.service;

import com.akerke.salonservice.domain.dto.AddressDTO;
import com.akerke.salonservice.domain.entity.Address;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.mapper.AddressMapper;
import com.akerke.salonservice.domain.repository.AddressRepository;
import com.akerke.salonservice.domain.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressMapper addressMapper;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        addressDTO = new AddressDTO(1L, "Street", "City", "State");

        address = new Address();
        address.setId(1L);

        addressRepository.deleteAll();
    }

    @Test
    void save_whenAddressIsSaved_thenReturnAddress() {
        address = addressMapper.toModel(addressDTO);

        when(addressMapper.toModel(addressDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);

        var savedAddress = addressService.save(addressDTO);

        assertEquals(address, savedAddress);
    }

    @Test
    void getById_whenValidId_thenReturnAddress() {
        var id = 1L;

        when(addressRepository.findById(id)).thenReturn(Optional.of(address));

        var returnedAddress = addressService.getById(id);

        assertEquals(address, returnedAddress);
    }

    @Test
    void getById_whenNonExistId_thenThrowException() {
        var id = 1L;

        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> addressService.getById(id));

        assertEquals("Address with id: 1 not found", entityNotFoundException.getMessage());
    }

    @Test
    void delete_whenNonExistId_thenNoAddressDeleted() {
        var id = 1L;

        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.delete(id));

        verify(addressRepository, times(0)).deleteById(id);
    }

    @Test
    void getAll_whenRepositoryContainsAddresses_thenReturnListOfAddresses() {
        var addresses = Collections.singletonList(address);

        when(addressRepository.findAll()).thenReturn(addresses);

        var returnedAddresses = addressService.getAll();

        assertEquals(addresses, returnedAddresses);
    }

    @Test
    void getAll_whenRepositoryIsEmpty_thenReturnEmptyList() {
        when(addressRepository.findAll()).thenReturn(List.of());

        var returnedAddresses = addressService.getAll();

        assertEquals(0, returnedAddresses.size());
    }

    @Test
    void delete_whenValidId_thenAddressDeleted() {
        var id = 1L;

        when(addressRepository.findById(id)).thenReturn(Optional.of(address));

        addressService.delete(id);

        verify(addressRepository, times(1)).delete(any(Address.class));
    }

    @Test
    public void update_whenAddressExists_thenAddressIsUpdated() {
        Long id = 1L;

        when(addressRepository.findById(id)).thenReturn(Optional.of(address));

        addressService.update(addressDTO, id);

        verify(addressMapper).update(addressDTO, address);
        verify(addressRepository).save(address);
    }

    @Test
    public void update_whenAddressDoesNotExist_thenThrowException() {
        Long id = 1L;

        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.update(addressDTO, id));
        verify(addressRepository, never()).save(any(Address.class));
    }

}