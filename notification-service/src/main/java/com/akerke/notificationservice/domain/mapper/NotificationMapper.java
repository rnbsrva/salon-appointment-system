package com.akerke.notificationservice.domain.mapper;

import com.akerke.notificationservice.domain.dto.NotificationDTO;
import com.akerke.notificationservice.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface NotificationMapper {

    @Mapping(target = "checked", expression = "java(false)")
    Notification toModel (NotificationDTO notificationDTO);

    NotificationDTO toDTO (Notification notification);

}
