package com.akerke.salonservice.domain.mapper;

import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.entity.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mapper(imports = {Master.class, ArrayList.class,
        Treatment.class, WorkDay.class, Feedback.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SalonMapper {

    @Mapping(target = "masters", expression = "java(new ArrayList<Master>())")
    @Mapping(target = "treatments", expression = "java(new ArrayList<Treatment>())")
    @Mapping(target = "workDays", expression = "java(new ArrayList<WorkDay>())")
    @Mapping(target = "imageMetadata", expression = "java(new ArrayList<>())")
    Salon toModel(SalonDTO salonDTO);

    @Mapping(target = "ownerId", expression = "java(salon.getOwner().getId())")
    SalonDTO toDTO(Salon salon);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    void update(SalonDTO salonDTO, @MappingTarget Salon salon);

    default List<String> treatmentList(Salon salon) {
        return salon.getTreatments()
                .stream()
                .map(Treatment::getName)
                .toList();
    }
}
