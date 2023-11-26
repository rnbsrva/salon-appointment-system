package com.akerke.mailsender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MailSenderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailSenderApplication.class, args);
        log.info("hello");
    }
}
