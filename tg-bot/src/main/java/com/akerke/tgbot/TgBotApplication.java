package com.akerke.tgbot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@SpringBootApplication
public class TgBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TgBotApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            ResourceBundleMessageSource messageSource
    ) {
        return args -> {
            System.out.println(messageSource.getMessage("lang.change", null, Locale.ENGLISH));
        };
    }

}
