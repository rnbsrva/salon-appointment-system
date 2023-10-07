package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.entity.Feedback;
import com.akerke.salonservice.entity.Master;
import com.akerke.salonservice.entity.Treatment;
import com.akerke.salonservice.entity.WorkDay;
import org.mapstruct.*;

import java.util.ArrayList;

@Mapper(imports = {ArrayList.class,
        Treatment.class, WorkDay.class, Feedback.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
public interface MasterMapper {

    @Mapping(target = "feedbacks", expression = "java(new ArrayList<>())")
    @Mapping(target = "workTimes", expression = "java(new ArrayList<>())")
    @Mapping(target = "appointments", expression = "java(new ArrayList<>())")
    @Mapping(target = "treatments", expression = "java(new ArrayList<>())")
    Master toModel (MasterDTO masterDTO);

    @Mapping(target = "userId", expression = "java(master.getUser().getId())")
    @Mapping(target = "salonId", expression = "java(master.getSalon().getId())")
    MasterDTO toDTO (Master master);

    @Mapping(target = "id", ignore = true)
    void update(MasterDTO masterDTO, @MappingTarget Master master);

}
