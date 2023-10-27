package com.akerke.chatservice.service;

import com.akerke.chatservice.model.Chat;
import com.akerke.chatservice.payload.request.ChatCreateRequest;

public interface ChatService {

    Chat createChat(ChatCreateRequest createRequest);
}
