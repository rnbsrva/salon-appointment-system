package com.akerke.authserver;

import com.akerke.authserver.common.annotations.Password;
import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.model.User;
import com.akerke.authserver.domain.repository.ReactiveUserRepository;
import com.akerke.authserver.domain.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class AuthServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            ReactiveUserRepository reactiveUserRepository,
            PasswordEncoder passwordEncoder,
            AuthService authService
    ) {
        return args -> {
            var user = new User();
            user.setEmail("kkraken2005@gmail.com");
            user.setPassword(passwordEncoder.encode("danekerscode"));
            var saved = reactiveUserRepository.save(user)
                    .block();

            System.out.println("saved " + saved.toString());

            System.out.println(authService.authenticate(new AuthDTO(user.getEmail(),"danekerscode")).block());
        };
    }


}
