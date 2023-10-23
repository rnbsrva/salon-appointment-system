package com.akerke.tgbot.dao;

import com.akerke.tgbot.exception.UserNotFoundException;
import com.akerke.tgbot.tg.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultUserDAO implements UserDAO {

    private final JdbcTemplate jdbc;

    @Override
    public User findByEmail(String email) {
        var queryResult = jdbc.query("""
                select * from users
                where email=
                """ + email, new BeanPropertyRowMapper<>(User.class)
        );

        return queryResult
                .stream()
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void update(User user) {
        jdbc.update(
                """
                        UPDATE users
                        set tg_id = ?,
                        set locale = ?
                        where id = ?
                        """,
                user.getTgId(), user.getLocale().name(), user.getId()
        );

    }

    @Override
    public User findByTelegramId(Long tgId) {
        var queryResult = jdbc.query("""
                select * from users
                where tg_id=
                """ + tgId, new BeanPropertyRowMapper<>(User.class)
        );

        return queryResult
                .stream()
                .findFirst()
                .orElseThrow(UserNotFoundException::new);

    }

}
