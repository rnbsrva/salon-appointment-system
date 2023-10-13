package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.entity.Master;
import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MasterMapperTest {

    private MasterMapper masterMapper;
    private Master master;
    private MasterDTO masterDTO;

    @BeforeEach
    void setUp(){
        masterMapper = Mappers.getMapper(MasterMapper.class);
        masterDTO = new MasterDTO(1L, 1L, "position", new Date(),"about");
        master = new Master();
    }

    @Test
    void update_WhenMasterDtoIsNull_thenNoChangesAreMade(){
        assertDoesNotThrow(()->masterMapper.update(null, master));
    }

    @Test
    void update_WhenMasterIsNull_thenNullPointerIsThrown(){
        assertThrows(NullPointerException.class, ()->masterMapper.update(masterDTO, null));
    }

    @Test
    void toModel_whenValidMasterDTO_thenMapsProperties(){
        var userId = 1L;
        var salonId= 1L;
        var position = "position";
        var experienceDate = new Date();
        var about = "about";

        var masterDTO = new MasterDTO(userId, salonId, position, experienceDate, about);

        var master = masterMapper.toModel(masterDTO);

        assertNotNull(master);
        assertEquals(position, master.getPosition());
        assertEquals(about, master.getAbout());
        assertEquals(experienceDate, master.getExperienceDate());

    }


    @Test
    void toDTO_whenMasterProvided_thenReturnedMasterDTO(){
        var user = new User();
        user.setId(1L);

        var salon = new Salon();
        salon.setId(1L);

        master.setUser(user);
        master.setSalon(salon);
        master.setAbout("about");
        master.setPosition("position");
        master.setExperienceDate(new Date());

        var masterDTO = masterMapper.toDTO(master);

        assertEquals(user.getId(), masterDTO.userId());
        assertEquals(salon.getId(), masterDTO.salonId());
        assertEquals(master.getAbout(), masterDTO.about());
        assertEquals(master.getPosition(), masterDTO.position());
        assertEquals(master.getExperienceDate(), masterDTO.experienceDate());

    }
}
