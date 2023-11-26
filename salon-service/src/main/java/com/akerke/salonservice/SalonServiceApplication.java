package com.akerke.salonservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SalonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalonServiceApplication.class, args);

        log.info("hello world");
    }

}
