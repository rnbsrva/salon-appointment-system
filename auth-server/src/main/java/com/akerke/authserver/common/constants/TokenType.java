package com.akerke.authserver.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {
    ACCESS,
    REFRESH,
}
