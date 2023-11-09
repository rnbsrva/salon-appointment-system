package com.akerke.tgbot.tg.entity;


import com.akerke.tgbot.tg.constants.NotificationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Notification {

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

