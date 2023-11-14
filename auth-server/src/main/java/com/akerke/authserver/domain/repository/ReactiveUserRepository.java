package com.akerke.authserver.domain.repository;

import com.akerke.authserver.domain.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveUserRepository extends
        ReactiveMongoRepository<User, String> {
    Mono<User> findByEmail(Mono<String> email);
}
