package com.akerke.authservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum PermissionType {

    SCOPE_READ("scope.read"),
    EMAIL("email"),

    SALON_WRITE("salon.write"),
    SALON_DELETE("salon.delete");
    private final String name;

    public static Set<PermissionType> defaultSet() {
        return EnumSet.of(
                PermissionType.EMAIL,
                PermissionType.SCOPE_READ
        );
    }

}
