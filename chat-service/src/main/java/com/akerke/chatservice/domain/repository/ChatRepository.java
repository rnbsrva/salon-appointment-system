package com.akerke.chatservice.domain.repository;

import com.akerke.chatservice.domain.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    void deleteAllByCreatedAtBefore(LocalDateTime createdAt);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'chatMessages' : 0 }")
    Optional<Chat> findByIdExcludingChatMessages(String id);

}
