package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.AddressDTO;
import com.akerke.salonservice.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private AddressMapper addressMapper;
    private AddressDTO addressDTO;
    private Address address;

    @BeforeEach
    void setUp() {
        addressMapper = Mappers.getMapper(AddressMapper.class);

        this.addressDTO = new AddressDTO(123L, "Street", "City", "State");
        this.address = new Address();

        address.setHouseNumber(456L);
        address.setStreet("Old Street");
        address.setCity("Old City");
        address.setState("Old State");
    }

    @Test
    void update_whenValidDTOAndEntity_thenUpdateEntity() {
        addressMapper.update(addressDTO, address);

        assertThat(address.getHouseNumber()).isEqualTo(addressDTO.houseNumber());
        assertThat(address.getStreet()).isEqualTo(addressDTO.street());
        assertThat(address.getCity()).isEqualTo(addressDTO.city());
        assertThat(address.getState()).isEqualTo(addressDTO.state());
    }

    @Test
    void update_WhenNullDTOAndNonNullEntity_thenNoUpdate() {
        addressDTO = null;

        addressMapper.update(addressDTO, address);

        assertThat(address.getHouseNumber()).isEqualTo(456L);
        assertThat(address.getStreet()).isEqualTo("Old Street");
        assertThat(address.getCity()).isEqualTo("Old City");
        assertThat(address.getState()).isEqualTo("Old State");
    }


    @Test
    void toDTO_whenAddressEntityGiven_thenAddressDTOReturned() {
        var addressDTO = addressMapper.toDTO(address);

        assertNotNull(addressDTO);
        assertThat(addressDTO.houseNumber()).isEqualTo(address.getHouseNumber());
        assertThat(addressDTO.street()).isEqualTo(address.getStreet());
        assertThat(addressDTO.city()).isEqualTo(address.getCity());
        assertThat(addressDTO.state()).isEqualTo(address.getState());
    }

    @Test
    void toDTO_whenNullAddressEntityGiven_thenNullAddressDTOReturned() {
        address = null;

        var addressDTO = addressMapper.toDTO(address);

        assertNull(addressDTO);
    }

    @Test
    void toModel_whenValidAddressDTO_thenReturnAddress() {
        var address = addressMapper.toModel(addressDTO);

        assertNotNull(address);
        assertEquals(addressDTO.houseNumber(), address.getHouseNumber());
        assertEquals(addressDTO.street(), address.getStreet());
        assertEquals(addressDTO.city(), address.getCity());
        assertEquals(addressDTO.state(), address.getState());
    }

    @Test
    void toModel_whenNullAddressDTO_thenReturnNull() {
        var address = addressMapper.toModel(null);

        assertNull(address);
    }
}