package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.UserDTO;
import com.akerke.salonservice.entity.*;
import org.mapstruct.*;

import java.util.ArrayList;

@Mapper(imports = {ArrayList.class,
        Appointment.class, Salon.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {


    @Mapping(target = "salons", expression = "java(new ArrayList<>())")
    @Mapping(target = "appointments", expression = "java(new ArrayList<>())")
    User toModel (UserDTO userDTO);

    UserDTO toDTO (User user);

    @Mapping(target = "id", ignore = true)
    void update(UserDTO userDTO, @MappingTarget User user);
}
