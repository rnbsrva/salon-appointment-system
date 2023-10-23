package com.akerke.tgbot.tg.entity;


import com.akerke.tgbot.tg.utils.CommonLocale;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private Long tgId;
    private String email;
    private CommonLocale locale;
}
