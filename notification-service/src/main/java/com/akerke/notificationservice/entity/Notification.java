package com.akerke.notificationservice.entity;

import com.akerke.notificationservice.constansts.NotificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    private Long recipientId;
    private String title;
    private String phoneNumber;
    private String message;
    private NotificationType type;
    private Long recipientTgId;
    private Boolean checked;

}
