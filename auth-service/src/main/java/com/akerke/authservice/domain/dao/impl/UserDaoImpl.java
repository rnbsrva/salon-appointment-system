package com.akerke.authservice.domain.dao.impl;

import com.akerke.authservice.domain.dao.UserDao;
import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.common.utils.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbc;

    private static final String USERS_TABLE = "users";

    @Override
    public void delete(String email) {
        var result = jdbc.update(
                "delete from users where email=?", email
        );

        log.info("delete user {} result {}", email, result);
    }

    @Override
    public void insert(User user) {
//        var result = jdbc.update(
//                "insert into users(name,surname,gender,phone,email) values (?,?,?,?,?);"
//                , user.getName()
//                , user.getSurname()
//                , user.getGender().name(),
//                user.getPhone(),
//                user.getEmail()
//        ); todo
    }

    @Override
    public void update(String email, Pair... pairs) {
        Arrays.stream(pairs)
                .filter(pair -> pair.key().equals("name") || pair.key().equals("surname"))
                .forEach(pair -> {
                    var result = jdbc.update(
                            "update users set " + pair.key() + " = ? where email=?", pair.value(), email
                    );

                    log.info("user update: {} email:{} result: {}", pair, email, result);
                });

    }


}
