package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.WorkDayDTO;
import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.WorkDay;
import com.akerke.salonservice.entity.WorkTime;
import org.mapstruct.*;

import java.util.ArrayList;

@Mapper(
        imports = {ArrayList.class, WorkTime.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface WorkDayMapper {

    @Mapping(target = "workTimes", expression = "java(new ArrayList<WorkTime>())")
    WorkDay toModel (WorkDayDTO workDayDTO);

    WorkDayDTO toDTO (WorkDay workDay);

    @Mapping(target = "id", ignore = true)
    void update (WorkDayDTO workDayDTO, @MappingTarget WorkDay workDay);

}
