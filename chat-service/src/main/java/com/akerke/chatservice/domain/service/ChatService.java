package com.akerke.chatservice.domain.service;

import com.akerke.chatservice.domain.model.Chat;
import com.akerke.chatservice.domain.request.ChatCreateRequest;

public interface ChatService {

    Chat createChat(ChatCreateRequest createRequest);
}
