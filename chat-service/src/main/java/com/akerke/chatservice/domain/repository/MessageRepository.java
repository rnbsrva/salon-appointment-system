package com.akerke.chatservice.domain.repository;

import com.akerke.chatservice.domain.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<ChatMessage,String> {
}
