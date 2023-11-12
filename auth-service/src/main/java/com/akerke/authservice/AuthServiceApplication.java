package com.akerke.authservice;

import com.akerke.authservice.infrastructure.kafka.KafkaEmailMessageDetails;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            KafkaTemplate<String, KafkaEmailMessageDetails> kafkaTemplate
    ) {
        return args -> {
            kafkaTemplate.send("email_verification", KafkaEmailMessageDetails.builder()
                    .msgBody("hello from dockerized mail sender")
                    .subject("some")
                    .recipient("orynbasarovaakerke88@gmail.com")
                    .build());
        };
    }

}
