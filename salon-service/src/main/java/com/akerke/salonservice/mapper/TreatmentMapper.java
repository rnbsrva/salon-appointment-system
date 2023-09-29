package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.TreatmentDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.Treatment;
import org.mapstruct.*;

import java.util.ArrayList;

@Mapper(imports = {ArrayList.class, Appointment.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TreatmentMapper {

    @Mapping(target = "appointments", expression = "java(new ArrayList<Appointment>())")
    Treatment toModel (TreatmentDTO treatmentDTO);

    TreatmentDTO toDTO (Treatment treatment);

    @Mapping(target = "id", ignore = true)
    void update (TreatmentDTO treatmentDTO, @MappingTarget Treatment treatment);

}
