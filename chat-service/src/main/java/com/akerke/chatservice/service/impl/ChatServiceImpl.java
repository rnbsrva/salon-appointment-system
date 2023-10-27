package com.akerke.chatservice.service.impl;

import com.akerke.chatservice.mapper.ChatMapper;
import com.akerke.chatservice.model.Chat;
import com.akerke.chatservice.payload.request.ChatCreateRequest;
import com.akerke.chatservice.repository.ChatRepository;
import com.akerke.chatservice.repository.UserRepository;
import com.akerke.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;


    @Override
    public Chat createChat(ChatCreateRequest createRequest) {
        var chat = chatMapper.toModel(createRequest);
        var user = userRepository.save(chat.getUser());

        chat.setUser(user);

        return chatRepository.save(chat);
    }
}
