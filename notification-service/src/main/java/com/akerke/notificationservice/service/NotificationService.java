package com.akerke.notificationservice.service;

import com.akerke.notificationservice.entity.Notification;

public interface NotificationService {

    Notification findById(Long id);

    void deleteById(Long id);
}
