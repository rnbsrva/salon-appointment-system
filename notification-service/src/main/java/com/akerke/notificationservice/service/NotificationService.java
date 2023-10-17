package com.akerke.notificationservice.service;

import com.akerke.notificationservice.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification findById(Long id);

    List<Notification> findAllByUser(Long id);

    void deleteById(Long id);

    void deleteByUser(Long id);
}
