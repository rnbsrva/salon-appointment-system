package com.akerke.authservice.payload.response;

import com.akerke.authservice.entity.User;
import com.akerke.authservice.utils.TokenDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token")
        TokenDetails accessToken,
        @JsonProperty("refresh_token")
        TokenDetails refreshToken,
        User user
) {

}
