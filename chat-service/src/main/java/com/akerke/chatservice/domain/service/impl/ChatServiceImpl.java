package com.akerke.chatservice.domain.service.impl;

import com.akerke.chatservice.domain.mapper.ChatMapper;
import com.akerke.chatservice.domain.model.Chat;
import com.akerke.chatservice.domain.repository.ChatRepository;
import com.akerke.chatservice.domain.repository.UserRepository;
import com.akerke.chatservice.domain.request.ChatCreateRequest;
import com.akerke.chatservice.domain.service.ChatService;
import com.akerke.exceptionlib.exception.EntityNotFoundException;
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

    @Override
    public void deleteChat(String chatId) {
        chatRepository.deleteById(chatId);
    }

    @Override
    public Chat getById(String id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Chat.class));
    }
}
