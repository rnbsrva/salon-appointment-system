package com.akerke.authservice.domain.dao;

import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.utils.Pair;

public interface UserDao {

    void delete(String email);

    void insert(User user);

    void update(String email, Pair ...pairs);

}
