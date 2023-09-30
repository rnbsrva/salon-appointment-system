package com.akerke.authservice.payload;

import com.akerke.authservice.utils.TokenDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token")
        TokenDetails accessToken,
        @JsonProperty("access_token")
        TokenDetails refreshToken
) {

}
