package com.akerke.notificationservice.mapper;

import com.akerke.notificationservice.common.constants.NotificationType;
import com.akerke.notificationservice.domain.mapper.NotificationMapper;
import com.akerke.notificationservice.domain.dto.NotificationDTO;
import com.akerke.notificationservice.domain.entity.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationMapperTest {

    private NotificationMapper notificationMapper;
    private Notification notification;
    private NotificationDTO notificationDTO;

    @BeforeEach
    public void setup() {
        notificationMapper = Mappers.getMapper(NotificationMapper.class);
        notification = new Notification();
        notificationDTO = new NotificationDTO(1L, "title", "message", NotificationType.SPECIAL_OFFER, "87771234567");

    }



    @Test
    public void ToModel_WhenValidNotificationDTOThenReturnNotification() {

        notification = notificationMapper.toModel(notificationDTO);

        assertEquals(notificationDTO.title(), notification.getTitle());
        assertEquals(notificationDTO.phoneNumber(), notification.getPhoneNumber());
        assertEquals(notificationDTO.message(), notification.getMessage());
        assertEquals(notificationDTO.type(), notification.getType());
        assertEquals(notificationDTO.recipientId(), notification.getRecipientId());
    }

    @Test
    public void ToDTO_WhenValidNotificationThenReturnNotificationDTO() {
        notification.setId(1L);
        notification.setTime(LocalDateTime.now());
        notification.setRecipientId(2L);
        notification.setTitle("Test Title");
        notification.setPhoneNumber("1234567890");
        notification.setMessage("Test Message");
        notification.setType(NotificationType.APPOINTMENT_CONFIRMATION);
        notification.setRecipientTgId(3L);
        notification.setChecked(true);

        notificationDTO = notificationMapper.toDTO(notification);

        // Assert
        assertEquals(notification.getRecipientId(), notificationDTO.recipientId());
        assertEquals(notification.getTitle(), notificationDTO.title());
        assertEquals(notification.getPhoneNumber(), notificationDTO.phoneNumber());
        assertEquals(notification.getMessage(), notificationDTO.message());
        assertEquals(notification.getType(), notificationDTO.type());
    }
}
