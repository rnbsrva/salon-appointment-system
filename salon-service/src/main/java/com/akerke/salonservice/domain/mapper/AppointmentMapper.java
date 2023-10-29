package com.akerke.salonservice.domain.mapper;

import com.akerke.salonservice.common.constants.Status;
import com.akerke.salonservice.domain.dto.AppointmentDTO;
import com.akerke.salonservice.domain.entity.Appointment;
import org.mapstruct.*;

@Mapper(imports = Status.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AppointmentMapper {

    @Mapping(target = "status", expression = "java(Status.PENDING)")
    Appointment toModel(AppointmentDTO appointmentDTO);

    @Mapping(target = "userId", expression = "java(appointment.getUser().getId())")
    @Mapping(target = "masterId", expression = "java(appointment.getMaster().getId())")
    @Mapping(target = "treatmentId", expression = "java(appointment.getTreatment().getId())")
    @Mapping(target = "workTimeId", expression = "java(appointment.getWorkTime().getId())")
    AppointmentDTO toDTO(Appointment appointment);

    @Mapping(target = "id", ignore = true)
    void update(AppointmentDTO appointmentDTO, @MappingTarget Appointment appointment);

}
