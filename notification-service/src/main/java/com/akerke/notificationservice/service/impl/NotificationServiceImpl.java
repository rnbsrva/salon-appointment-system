package com.akerke.notificationservice.service.impl;

import com.akerke.notificationservice.entity.Notification;
import com.akerke.notificationservice.exception.NotificationNotFoundException;
import com.akerke.notificationservice.repository.NotificationRepository;
import com.akerke.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(()->new NotificationNotFoundException(id));
    }

    @Override
    public List<Notification> findAllByUser(Long id){
        return notificationRepository.findAllByRecipientId(id);
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void deleteByUser(Long id) {
        notificationRepository.deleteNotificationsByRecipientId(id);
    }
}
