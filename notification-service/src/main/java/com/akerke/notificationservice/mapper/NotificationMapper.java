package com.akerke.notificationservice.mapper;

import com.akerke.notificationservice.dto.NotificationDTO;
import com.akerke.notificationservice.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface NotificationMapper {

    Notification toModel (NotificationDTO notificationDTO);

    NotificationDTO toDTO (Notification notification);

}
