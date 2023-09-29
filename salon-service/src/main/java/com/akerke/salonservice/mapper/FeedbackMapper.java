package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.FeedbackDTO;
import com.akerke.salonservice.entity.*;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FeedbackMapper {


    Feedback toModel (FeedbackDTO feedbackDTO);

    @Mapping(target = "userId", expression = "java(feedback.getUser().getId())")
    @Mapping(target = "appointmentId", expression = "java(feedback.getAppointment().getId())")
    FeedbackDTO toDTO(Feedback feedback);

    @Mapping(target = "id", ignore = true)
    void update (FeedbackDTO feedbackDTO, @MappingTarget Feedback feedback);

}
