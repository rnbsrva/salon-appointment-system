package com.akerke.authserver.common.jwt;

import com.akerke.authserver.common.constants.TokenType;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.akerke.authserver.common.jwt.JwtService.TOKEN_CLAIM_KEY;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        final String authHeader = req.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        final DecodedJWT decodedJWT;

        if (!req.getRequestURL().toString().contains("auth") || !req.getRequestURL().toString().contains("role")) {
            var ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);

            chain.doFilter(req, res);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            var ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);

            chain.doFilter(req, res);
            return;
        }

        jwt = authHeader.substring(7);
        decodedJWT = jwtService.convertToken(jwt);

        if (decodedJWT.getClaim(TOKEN_CLAIM_KEY).as(TokenType.class) != TokenType.ACCESS) {
            res.sendError(401, "Token type not access");
            return;
        }

        userEmail = decodedJWT.getSubject();
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userEmail,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(req)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(req, res);
    }

}
