package com.akerke.authservice;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.payload.request.RegistrationRequest;
import com.akerke.authservice.repository.UserRepository;
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
            UserRepository userRepository,
            UserService userService,
            AuthService authService
    ) {
        return args -> {
            userRepository.deleteAll();

            var user = userService.register(
                    new RegistrationRequest(
                            "name",
                            "surnmae",
                            "kkraken@gmail.com",
                            "777 777 77 77"
                    )
            );

            var tokens = authService.token(user);
            System.out.println(tokens);

            System.out.println("*".repeat(15));

            var statusResponse = authService.validateToken(tokens.accessToken().token(), TokenType.ACCESS_TOKEN);

            System.out.println(statusResponse);
        };
    }
}
