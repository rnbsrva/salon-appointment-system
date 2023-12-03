package com.akerke.authserver.common.jwt;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.domain.dto.RouteValidateDTO;
import com.akerke.authserver.domain.dto.StatusResponseDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtRouteValidator {

    private final JwtService jwtService;
    private Map<SecurityRole, List<RouteRequirements>> roleRouteRequirementsMap;

    public JwtRouteValidator(
            JwtService jwtService
    ) {
        this.jwtService = jwtService;//todo

        this.roleRouteRequirementsMap = new HashMap<>() {{
            put(SecurityRole.ADMIN, new ArrayList<>() {{
                add(new RouteRequirements(
                        HttpMethod.GET, List.of("")
                ));
            }});
        }};

    }

    private record RouteRequirements(
            HttpMethod httpMethod,
            List<String> routes
    ) {
    }


    public StatusResponseDTO canActivate(
            RouteValidateDTO routeValidate
    ) {

        return new StatusResponseDTO(true);// TODO: 11/17/2023
    }
}
