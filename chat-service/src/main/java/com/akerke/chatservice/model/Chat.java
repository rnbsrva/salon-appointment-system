package com.akerke.chatservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "chat_ ")
public class Chat {

    @Id
    private String id;
    private LocalDateTime createdAt;
    private Long salonId;
    private User user;

}
