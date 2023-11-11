package com.akerke.mailsender;

import com.akerke.mailsender.dto.EmailDetails;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class MailSenderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailSenderApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            KafkaTemplate<String, EmailDetails> kafkaTemplate
    ){
        return args -> {
            kafkaTemplate.send(
                    "email_verification",
                    new EmailDetails(
                            "kkraken2005@gmail.com",
                            "https://www.google.com",
                            "hello",
                            ""
                    )
            );
        };
    }
}
