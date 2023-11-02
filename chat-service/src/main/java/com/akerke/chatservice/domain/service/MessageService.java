package com.akerke.chatservice.domain.service;

import com.akerke.chatservice.domain.model.ChatMessage;

import java.util.List;
import java.util.Set;

public interface MessageService {

    void save(ChatMessage chatMessage, String key);

    List<ChatMessage> get(String key);

    void clean(String key);

    void delete(String key, ChatMessage chatMessage);

    void add(String key, ChatMessage chatMessage);

    Set<String> keys(String pattern);

}
