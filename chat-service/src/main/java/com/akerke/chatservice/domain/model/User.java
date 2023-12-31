package com.akerke.chatservice.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "user_")
public class User {
    @Id
    private String id;

    private String name;
    private String surname;
    private Long applicationId;
    private LocalDateTime lastSeen;
}
