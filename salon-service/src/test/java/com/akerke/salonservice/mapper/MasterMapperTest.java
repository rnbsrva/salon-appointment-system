package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.entity.Master;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

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


}
