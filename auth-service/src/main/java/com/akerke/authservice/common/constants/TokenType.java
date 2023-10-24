package com.akerke.authservice.common.constants;

import com.akerke.authservice.utils.Expirable;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.jsonwebtoken.SignatureAlgorithm.*;

/**
 * An enumeration representing token types used in a token-based authentication system.
 * These token types serve various purposes in the authentication and authorization process.
 */
@RequiredArgsConstructor
@Getter
public enum TokenType implements Expirable { // todo read expiration minute from yml

    /**
     * Represents an access token.
     * Access tokens are typically short-lived and use the HS384 signing algorithm.
     * They are used to grant access to protected resources, such as API endpoints, for a limited time.
     * Access tokens are commonly used for user authentication and authorization.
     */
    ACCESS_TOKEN(HS384, "access_token") {
        @Override
        public Integer getExpirationMinute() {
            return 60;
        }
    },
    /**
     * Represents a refresh token.
     * Refresh tokens are usually long-lived and use the HS512 signing algorithm.
     * They are used to obtain new access tokens without requiring the user to re-enter their credentials.
     * Refresh tokens are essential for maintaining continuous user sessions and improving security.
     */
    REFRESH_TOKEN(HS512, "refresh_token") {
        @Override
        public Integer getExpirationMinute() {
            return 1440;
        }
    },

    FORGOT_PASSWORD_TOKEN(HS384, "forgot_password_token"){
        @Override
        public Integer getExpirationMinute() {
            return 10;
        }
    },

    /**
     * Represents a verification token.
     * Verification tokens are used for email confirmation or account activation.
     * They have a short expiration time to ensure timely email verification.
     * Verification tokens help verify the authenticity of the email address provided during registration.
     */
    EMAIL_VERIFICATION_TOKEN(HS256, "verification_token") {
        @Override
        public Integer getExpirationMinute() {
            return 10;
        }
    };

    /**
     * The signature algorithm used for signing tokens of this type.
     */


    private final SignatureAlgorithm algorithm;
    private final String name;


}

