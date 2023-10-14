package com.akerke.authservice.dao.impl;

import com.akerke.authservice.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbc;

    @Override
    public void delete(String email) {

    }
}
