package com.akerke.tgbot.dao;

import com.akerke.tgbot.tg.entity.User;

public interface UserDAO {

    User findByEmail(String email);

    void update(User user);

    User findByTelegramId(Long id);
}
