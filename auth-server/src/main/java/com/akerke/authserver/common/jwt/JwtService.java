package com.akerke.authserver.common.jwt;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.token.issuer}")
    private String issuer;

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.token.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private final ReactiveMongoTemplate reactiveMongo;

    private final static String TOKEN_CLAIM_KEY = "type";
    private final static String ROLES_CLAIM_KEY = "roles";

    public String createToken(
            User user,
            TokenType token
    ) {
        var date = Date.from(
                ZonedDateTime.now()
                        .plusSeconds(token == TokenType.ACCESS ? accessTokenExpiration : refreshTokenExpiration)
                        .toInstant()
        );
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuer(issuer)
                .withClaim(TOKEN_CLAIM_KEY, token.name())
                .withClaim(
                        ROLES_CLAIM_KEY,
                        user.getRoles()
                                .stream()
                                .map(SecurityRole::name)
                                .toList()
                )
                .withIssuedAt(new Date())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC512(secret));
    }

    public User validateToken(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC512(secret))
                .withIssuer(issuer)
                .build();

        var jwt = verifier.verify(token);
        System.out.println(jwt.getClaims());

        Query query = new Query(Criteria.where("email").is(jwt.getSubject()));

        return reactiveMongo.find(query, User.class)
                .blockFirst();
    }

}
