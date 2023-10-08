package com.akerke.chatservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;
    private Long userId;
    private Long chatId;
    private Date date;
    private String message;
    private Long parentMessageId;
}
