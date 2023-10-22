package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.AddressDTO;
import com.akerke.salonservice.dto.SalonDTO;
import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SalonMapperTest {

    private SalonMapper salonMapper;
    private Salon salon;
    private SalonDTO salonDTO;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp(){
        salonMapper = Mappers.getMapper(SalonMapper.class);
        salon = new Salon();
        addressDTO =  new AddressDTO(1L, "street", "city", "state");
        salonDTO = new SalonDTO(1L, "name", "87771234567", "inf@gmail.com", addressDTO, "description");
    }

    @Test
    void toModel_whenNullSalonDTO_whenReturnNull(){
        var salon = salonMapper.toModel(null);
        assertNull(salon);
    }

    @Test
    void toDTO_whenSalonNull_thenReturnNull(){
        assertNull(salonMapper.toDTO(null));
    }

    @Test
    void update_whenSalonEntityIsUpdated_thenSuccess() {
        salonMapper.update(salonDTO, salon);

        assertEquals(salonDTO.description(), salon.getDescription());
        assertEquals(salonDTO.ownerId(), salon.getUserId());
        assertEquals(salonDTO.name(), salon.getName());
        assertEquals(salonDTO.phone(), salon.getPhone());
        assertEquals(salonDTO.email(), salon.getEmail());
    }

    @Test
    void toDTO_whenSalonNotNull_thenReturnFeedbackDTO () {
        salon.setName("newName");
        salon.setDescription("newDescription");
        salon.setEmail("newEmail");
        salon.setPhone("newPhone");
        var salonDTO = salonMapper.toDTO(salon);

        assertEquals(salonDTO.email(), salon.getEmail());
        assertEquals(salonDTO.name(), salon.getName());
        assertEquals(salonDTO.phone(), salon.getPhone());
        assertEquals(salonDTO.description(), salon.getDescription());
    }

}
