package com.akerke.notificationservice.domain.service.impl;

import com.akerke.notificationservice.domain.mapper.NotificationMapper;
import com.akerke.notificationservice.domain.repository.NotificationRepository;
import com.akerke.notificationservice.domain.service.NotificationService;
import com.akerke.notificationservice.domain.dto.NotificationDTO;
import com.akerke.notificationservice.domain.entity.Notification;
import com.akerke.notificationservice.common.exception.NotificationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper mapper;

    @Override
    public void save(NotificationDTO notificationDTO) {
        notificationRepository.save(mapper.toModel(notificationDTO));
    }

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
        notificationRepository.delete(this.findById(id));
    }

    @Override
    public void deleteByUser(Long id) {
        var notifications = this.findAllByUser(id);
        for (Notification notification:  notifications) {
            this.deleteById(notification.getId());
        }
    }
}
