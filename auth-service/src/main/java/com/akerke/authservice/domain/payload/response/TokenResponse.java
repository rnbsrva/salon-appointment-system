package com.akerke.authservice.domain.payload.response;

import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.common.utils.TokenDetails;

public record TokenResponse(
        TokenDetails accessToken,
        TokenDetails refreshToken,
        User user
) {

}
