package com.akerke.chatservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Chat {
    private Long id; // salon name
    private String name; // salon name
    private List<User> members;
    private List<Message> messages;
}
