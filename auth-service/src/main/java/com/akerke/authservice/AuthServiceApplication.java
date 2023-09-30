package com.akerke.authservice;

import com.akerke.authservice.controller.UserController;
import com.akerke.authservice.payload.request.RegistrationRequest;
import com.akerke.authservice.service.AuthService;
import com.akerke.authservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            UserService userService,
            AuthService authService
    ) {
        return args -> {
            var user = userService.register(
                    new RegistrationRequest(
                            "name",
                            "surnmae",
                            "kkraken@gmail.com",
                            "777 777 77 77"
                    )
            );
            System.out.println(authService.token(user));
        };
    }
}
