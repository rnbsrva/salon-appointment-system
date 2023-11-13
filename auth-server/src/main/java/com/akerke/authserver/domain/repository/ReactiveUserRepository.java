package com.akerke.authserver.domain.repository;

import com.akerke.authserver.domain.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveUserRepository extends ReactiveMongoRepository<User,String> {
}
