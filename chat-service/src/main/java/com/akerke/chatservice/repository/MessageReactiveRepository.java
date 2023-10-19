package com.akerke.chatservice.repository;

import com.akerke.chatservice.model.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageReactiveRepository extends ReactiveMongoRepository<Message,String> {
}
