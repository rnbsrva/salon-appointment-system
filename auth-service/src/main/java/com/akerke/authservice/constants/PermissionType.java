package com.akerke.authservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PermissionType {

    SALON_WRITE("salon.write"),
    SALON_DELETE("salon.delete"),
    SALON_READ("salon.read");


    private final String name;
}
