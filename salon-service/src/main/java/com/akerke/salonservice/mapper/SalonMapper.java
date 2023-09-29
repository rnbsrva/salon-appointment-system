package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.SalonDTO;
import com.akerke.salonservice.entity.*;
import org.mapstruct.*;

import java.util.ArrayList;

@Mapper(imports = {Master.class, ArrayList.class,
        Treatment.class, WorkDay.class, Feedback.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SalonMapper {

    @Mapping(target = "masters", expression = "java(new ArrayList<Master>())")
    @Mapping(target = "treatments", expression = "java(new ArrayList<Treatment>())")
    @Mapping(target = "workDays", expression = "java(new ArrayList<WorkDay>())")
    @Mapping(target = "feedbacks", expression = "java(new ArrayList<Feedback>())")
    Salon toModel(SalonDTO salonDTO);

    SalonDTO toDTO (Salon salon);

    @Mapping(target = "id", ignore = true)
    void update(SalonDTO salonDTO, @MappingTarget Salon salon);


}
