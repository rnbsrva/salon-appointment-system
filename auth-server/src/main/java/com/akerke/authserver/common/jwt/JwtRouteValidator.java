package com.akerke.authserver.common.jwt;

import com.akerke.authserver.common.exception.InvalidCredentialsException;
import com.akerke.authserver.domain.dto.RouteValidateDTO;
import com.akerke.authserver.domain.dto.StatusResponseDTO;
import com.akerke.authserver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.akerke.authserver.common.constants.SecurityRole.USER;
import static com.akerke.authserver.common.constants.SecurityRole.values;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRouteValidator {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    public StatusResponseDTO canActivate(
            RouteValidateDTO routeValidate
    ) {
        var split = routeValidate.route().split("/");
        var securityRole = Arrays.stream(values())
                .filter(s -> Arrays.stream(split).anyMatch(e -> e.equals(s.name().toLowerCase())))
                .findFirst()
                .orElse(USER);

        try {
            var canActivate = false;
            var decodedJwt = jwtService.convertToken(routeValidate.token());
            var userEmail = decodedJwt.getSubject();
            var user = userRepository.findUserByEmail(userEmail)
                    .orElseThrow(InvalidCredentialsException::new);

            if (user.getEmailConfirmed() && user.getRoles().stream().anyMatch(r ->r.ordinal() >= securityRole.ordinal())) {
                canActivate = true;
                log.info("user {} can activate: {}", user.getEmail(), routeValidate.route());
            }else {
                log.info("user {} can not activate: {}", user.getEmail(), routeValidate.route());
            }

            return new StatusResponseDTO(canActivate);
        } catch (Exception e) {
            log.error("canActivate err {}", e.getMessage());
            return new StatusResponseDTO(false);
        }
    }
}

