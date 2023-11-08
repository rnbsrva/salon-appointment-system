package com.akerke.notificationservice.domain.repository;

import com.akerke.notificationservice.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByRecipientId(Long recipientId);

    void deleteNotificationsByRecipientId(Long recipientId);

}
