package com.akerke.authserver.common.jwt;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.domain.model.Token;
import com.akerke.authserver.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.token.issuer}")
    private String issuer;

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.token.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    public final static String TOKEN_CLAIM_KEY = "type";
    public final static String ROLES_CLAIM_KEY = "roles";

    public Token createToken(
            User user,
            TokenType tokenType
    ) {

        var lifeTime = tokenType == TokenType.ACCESS ? accessTokenExpiration : refreshTokenExpiration;
        var expirationDate = Date.from(
                ZonedDateTime.now()
                        .plusSeconds(lifeTime)
                        .toInstant()
        );


        Date creationDate = new Date();

        var token =  JWT.create()
                .withSubject(user.getEmail())
                .withIssuer(issuer)
                .withClaim(TOKEN_CLAIM_KEY, tokenType.name())
                .withClaim(
                        ROLES_CLAIM_KEY,
                        user.getRoles()
                                .stream()
                                .map(SecurityRole::name)
                                .toList()
                )
                .withIssuedAt(creationDate)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC512(secret));

        return Token.builder()
                .created(creationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .subject(user.getEmail())
                .value(token)
                .lifeTime(lifeTime)
                .type(tokenType)
                .build();
    }

    public DecodedJWT convertToken(
            String token
    ) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC512(secret))
                .withIssuer(issuer)
                .build();
        try {
            return verifier.verify(token);
        } catch (Exception e) {
            log.error("convert token error {}", e.getMessage());
            return null;
        }
    }

}
