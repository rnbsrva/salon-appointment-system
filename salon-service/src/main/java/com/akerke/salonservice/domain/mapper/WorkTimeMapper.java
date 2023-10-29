package com.akerke.salonservice.domain.mapper;

import com.akerke.salonservice.domain.dto.WorkTimeDTO;
import com.akerke.salonservice.domain.entity.WorkTime;
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
