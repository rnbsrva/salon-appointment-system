package com.akerke.authservice.common.logger;

import com.akerke.authservice.common.utils.LogBody;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LogFilter extends OncePerRequestFilter {

    private final CommonLogger logger;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    )
            throws ServletException, IOException {

        var additionalLogProperties = new LogBody(
                HttpStatus.valueOf(response.getStatus()),
                request.getRequestURI(),
                HttpMethod.valueOf(request.getMethod())
        );

        logger.info("new request to server [{" + additionalLogProperties + "}]", additionalLogProperties);

        filterChain.doFilter(request, response);
    }

}
