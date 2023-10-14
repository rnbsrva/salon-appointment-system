package com.akerke.authservice.service.impl;

import com.akerke.authservice.constants.EmailLinkMode;
import com.akerke.authservice.constants.SecurityRole;
import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.kafka.KafkaEmailMessageDetails;
import com.akerke.authservice.kafka.KafkaManageRoleRequest;
import com.akerke.authservice.kafka.KafkaProducer;
import com.akerke.authservice.payload.request.ForgotPasswordRequest;
import com.akerke.authservice.payload.request.ResetPasswordRequest;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.payload.response.TokenResponse;
import com.akerke.authservice.security.EmailLinkHelper;
import com.akerke.authservice.security.JwtUtil;
import com.akerke.authservice.security.validate.TokenValidator;
import com.akerke.authservice.service.AuthService;
import com.akerke.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


import static com.akerke.authservice.constants.AppConstants.*;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwt;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducer kafka;
    private final EmailLinkHelper linkHelper;
    private final Map<TokenType, TokenValidator> tokenValidator;


    public AuthServiceImpl(
            JwtUtil jwt,
            UserService userService,
            PasswordEncoder passwordEncoder,
            KafkaProducer kafka,
            EmailLinkHelper linkHelper,
            List<TokenValidator> tokenValidators
    ) {
        this.jwt = jwt;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.kafka = kafka;
        this.linkHelper = linkHelper;
        this.tokenValidator = tokenValidators
                .stream()
                .collect(Collectors.toMap(TokenValidator::getType, Function.identity()));
    }

    @Override
    public void manageRole(
            KafkaManageRoleRequest kafkaManageRoleRequest,
            SecurityRole securityRole
    ) {
        // TODO: 10/14/2023      
    }

    @Override
    public StatusResponse validateToken(String token, TokenType type) {
        var validator = tokenValidator.get(type);
        return validator.validate(token);
    }

    @Override
    public TokenResponse token(User user) {
        return jwt.generateTokenResponse(user);
    }

    @Override
    public StatusResponse resetPassword(
            ResetPasswordRequest req
    ) {
        var user = userService.findByEmail(req.email());

        var validPassword = passwordEncoder.matches(req.oldPassword(), user.getPassword());

        if (!validPassword) {
            return StatusResponse.fail("invalid password");
        }

        if (!req.newPassword().equals(req.newPasswordConfirmation())) {
            return StatusResponse.fail("new password and password confirmation not match. try again");
        }

        user.setPassword(passwordEncoder.encode(req.newPassword()));

        userService.update(user);

        return StatusResponse.success();
    }

    @Override
    public StatusResponse forgotPassword(String email) {
        var user = userService.findByEmail(email);

        log.info("user {} forgot password", user.getId());

        var msg = KafkaEmailMessageDetails
                .builder()
                .subject("Forgot password action")
                .recipient(email)
                .msgBody(linkHelper.link(EmailLinkMode.FORGOT_PASSWORD_LINK, email))
                .build();

        kafka.produce(KAFKA_PASSWORD_FORGOT_TOPIC, msg);

        return StatusResponse.success();
    }

    @Override
    public StatusResponse confirmForgotPassword(
            ForgotPasswordRequest req, String token
    ) {
        var response = validateToken(token, TokenType.FORGOT_PASSWORD_TOKEN);
        if (!response.getSuccess()) {
            return response;
        }

        if (!req.newPassword().equals(req.newPasswordConfirmation())) {
            return StatusResponse.fail("password and password confirmation not match");
        }

        var claims = jwt.extractAllClaims(token);
        var email = claims.getSubject();
        var user = userService.findByEmail(email);

        user.setPassword(passwordEncoder.encode(req.newPassword()));

        userService.update(user);
        return StatusResponse.success();
    }


}
