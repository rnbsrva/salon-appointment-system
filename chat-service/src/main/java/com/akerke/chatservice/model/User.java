package com.akerke.chatservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user_")
public class User {
    @Id
    private String id;

    private String name;
    private String surname;
    private Long applicationId;
}
