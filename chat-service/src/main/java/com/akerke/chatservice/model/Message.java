package com.akerke.chatservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "message_")
public class Message {

    @Id
    private String id;

    private String content;

    private Boolean fromStuff;

    private String chatId;
}
