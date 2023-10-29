package com.akerke.salonservice.domain.mapper;

import com.akerke.salonservice.domain.dto.TreatmentDTO;
import com.akerke.salonservice.domain.entity.Appointment;
import com.akerke.salonservice.domain.entity.Master;
import com.akerke.salonservice.domain.entity.Treatment;
import org.mapstruct.*;

import java.util.ArrayList;

@Mapper(
        imports = {
                ArrayList.class,
                Appointment.class,
                Master.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface TreatmentMapper {

    @Mapping(target = "appointments", expression = "java(new ArrayList<>())")
    @Mapping(target = "masters", expression = "java(new ArrayList<>())")
    Treatment toModel(TreatmentDTO treatmentDTO);

    TreatmentDTO toDTO(Treatment treatment);

    @Mapping(target = "id", ignore = true)
    void update(TreatmentDTO treatmentDTO, @MappingTarget Treatment treatment);

}
