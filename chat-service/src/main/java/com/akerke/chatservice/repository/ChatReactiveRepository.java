package com.akerke.chatservice.repository;

import com.akerke.chatservice.model.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface ChatReactiveRepository extends ReactiveMongoRepository<Chat,String> {

    /**
     * Retrieve a Flux of Chat objects that were created before a specified LocalDateTime.
     * This method allows you to query the repository for Chat objects based on their creation
     * timestamp and return a Flux of matching Chat objects. The Flux will emit Chat objects
     * in the order they are retrieved from the data source.
     *
     * @param createdAt A Mono emitting the LocalDateTime representing the upper time boundary.
     *                  Only Chat objects created before this LocalDateTime will be included in the Flux.
     * @return A Flux of Chat objects that were created before the specified LocalDateTime.
     */
    public Flux<Chat> findChatsByCreatedAtBefore(Mono<LocalDateTime> createdAt);


}
