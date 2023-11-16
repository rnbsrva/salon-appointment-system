package com.akerke.authserver.domain.router;

import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.service.AuthService;
import com.akerke.authserver.domain.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AuthRouter {

    private final AuthServiceImpl authService;

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return route()
                .POST("/api/v1/auth/register", accept(APPLICATION_JSON), authService::register
                ).build();
    }

}
