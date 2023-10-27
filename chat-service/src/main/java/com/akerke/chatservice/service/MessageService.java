package com.akerke.chatservice.service;

import com.akerke.chatservice.model.Message;

import java.util.List;

public interface MessageService {

    void save(Message message, String key);

    List<Message> get(String salonId);

    void clean(String key);

}
