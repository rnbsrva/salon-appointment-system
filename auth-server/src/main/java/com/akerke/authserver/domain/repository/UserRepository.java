package com.akerke.authserver.domain.repository;

import com.akerke.authserver.domain.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserRepository extends
        MongoRepository<User, String> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
