package com.akerke.chatservice;

import com.akerke.chatservice.domain.service.UserStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@Slf4j
public class ChatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserStatusService userStatusService){
        return args -> {
            log.info("wh");
        };
    }
}