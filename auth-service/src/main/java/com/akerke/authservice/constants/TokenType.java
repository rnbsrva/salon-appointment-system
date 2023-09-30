package com.akerke.authservice.constants;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static io.jsonwebtoken.SignatureAlgorithm.*;

/**
 * An enumeration representing token types used in a token-based authentication system.
 * These token types serve various purposes in the authentication and authorization process.
 */
@AllArgsConstructor
@Getter
public enum TokenType {
    /**
     * Represents an access token.
     * Access tokens are typically short-lived and use the HS384 signing algorithm.
     * They are used to grant access to protected resources, such as API endpoints, for a limited time.
     * Access tokens are commonly used for user authentication and authorization.
     */
    ACCESS_TOKEN(60, HS384),

    /**
     * Represents a refresh token.
     * Refresh tokens are usually long-lived and use the HS512 signing algorithm.
     * They are used to obtain new access tokens without requiring the user to re-enter their credentials.
     * Refresh tokens are essential for maintaining continuous user sessions and improving security.
     */
    REFRESH_TOKEN(1440, HS512),

    /**
     * Represents a verification token.
     * Verification tokens are used for email confirmation or account activation.
     * They have a short expiration time to ensure timely email verification.
     * Verification tokens help verify the authenticity of the email address provided during registration.
     */
    VERIFICATION_TOKEN(10, HS256);

    /**
     * The expiration time of the token in minutes.
     */
    private final Integer expirationMinute;

    /**
     * The signature algorithm used for signing tokens of this type.
     */
    private final SignatureAlgorithm algorithm;
}

