package com.akerke.authserver;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.common.jwt.JwtService;
import com.akerke.authserver.domain.model.User;
import com.akerke.authserver.domain.repository.ReactiveUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(JwtService jwtService) {
        return args -> {
            var user = new User();
            user.setRoles(List.of(SecurityRole.APPLICATION_ADMIN));
            user.setEmail("email@gmail.com");
            var token = jwtService.createToken(user, TokenType.ACCESS);
            System.out.println(token);
            System.out.println(jwtService.validateToken(token));
        };
    }


}
