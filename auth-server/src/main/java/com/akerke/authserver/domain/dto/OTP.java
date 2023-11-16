package com.akerke.authserver.domain.dto;


public record OTP(
        Integer otp,
        String email
) {
}
