package com.akerke.authservice.security;

import com.akerke.authservice.constants.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@Slf4j
public class JwtUtil {

    private @Value("${auth.jwt.issuer}") String issuer;
    private @Value("${auth.jwt.secret}") String secret;

    public String generateToken(TokenType token, Map<String, Object> claims, String subject) {
        var expiration = Date.from(ZonedDateTime.now().plusMinutes(token.getExpirationMinute()).toInstant());
        return Jwts.builder()
                .setClaims(new HashMap<>(claims))
                .setIssuer(issuer)
                .setSubject(subject)
                .setId(randomId.get())
                .setIssuedAt(currentDate.get())
                .setExpiration(expiration)
                .signWith(signKey(), token.getAlgorithm())
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private final Supplier<String> randomId = () -> UUID.randomUUID().toString();
    private final Supplier<Date> currentDate = Date::new;

}