package com.akerke.notificationservice.domain.service;

import com.akerke.notificationservice.domain.dto.NotificationDTO;
import com.akerke.notificationservice.domain.entity.Notification;

import java.util.List;

public interface NotificationService {

    void save (NotificationDTO notificationDTO);

    Notification findById(Long id);

    List<Notification> findAllByUser(Long id);

    void deleteById(Long id);

    void deleteByUser(Long id);
}
