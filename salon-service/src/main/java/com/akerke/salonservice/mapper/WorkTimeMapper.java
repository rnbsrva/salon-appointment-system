package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.WorkTimeDTO;
import com.akerke.salonservice.entity.WorkTime;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface WorkTimeMapper {

    WorkTime toModel (WorkTimeDTO workTimeDTO);

    @Mapping(target = "workDayId", expression = "java(workTime.getWorkDay().getId())")
    WorkTimeDTO toDTO (WorkTime workTime);

    void update (WorkTimeDTO workTimeDTO, @MappingTarget WorkTime workTime);

}
