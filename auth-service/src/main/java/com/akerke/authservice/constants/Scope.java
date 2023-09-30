package com.akerke.authservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum Scope {

    SCOPE_READ("scope.read"),
    EMAIL("email"),



    SALON_WRITE("salon.write"),
    SALON_DELETE("salon.delete");


    private final String name;

    public static Set<Scope> defaultSet() {
        return EnumSet.of(
                Scope.SCOPE_READ
        );
    }

}
