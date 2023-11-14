package com.akerke.authserver.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record OTP(
        @Min(100_000)
        @Max(999_999)
        Integer otp
) {
}
