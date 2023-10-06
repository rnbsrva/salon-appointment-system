package com.akerke.notificationservice.service.impl;

import com.akerke.notificationservice.entity.Notification;
import com.akerke.notificationservice.exception.NotificationNotFoundException;
import com.akerke.notificationservice.repository.NotificationRepository;
import com.akerke.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification findById(Long id) {
        var s = notificationRepository.findById(id);
        s.ifPresent(System.out::println);
        var d = new Notification();
        d.setId(id);
        d.setTitle("Re");
        return d;
    }


    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }
}
