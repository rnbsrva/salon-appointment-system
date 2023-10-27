package com.akerke.chatservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "message_")
public class Message {

    @Id
    private String id;
    private String content;
    private Boolean fromStuff;
    private Long chatId;
    private Boolean received;
    private Long userId;
    private List<Long> files;
    private LocalDateTime time;
}
