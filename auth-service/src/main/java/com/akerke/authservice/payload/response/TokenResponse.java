package com.akerke.authservice.payload.response;

import com.akerke.authservice.entity.User;
import com.akerke.authservice.utils.TokenDetails;

public record TokenResponse(
        TokenDetails accessToken,
        TokenDetails refreshToken,
        User user
) {

}
