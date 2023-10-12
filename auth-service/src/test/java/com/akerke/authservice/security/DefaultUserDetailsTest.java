package com.akerke.authservice.security;

import com.akerke.authservice.constants.Scope;
import com.akerke.authservice.entity.Permission;
import com.akerke.authservice.entity.Role;
import com.akerke.authservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DefaultUserDetailsTest {

    private User user;
    private DefaultUserDetails userDetails;

    @BeforeEach
    void setUp() {
        user = new User();

        user.setPassword("helloWorld4317!");
        user.setEmail("hello@gmail.com");

        var role = new Role();
        role.setUser(user);

        var permissions = new HashSet<Permission>() {{
            add(new Permission(Scope.SCOPE_READ, role));
        }};

        role.setPermissions(permissions);

        user.setRoles(new ArrayList<>() {{
            add(role);
        }});

        userDetails = new DefaultUserDetails(user);
    }

    @Test
    void getPassword_thenReturnUserPassword() {
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void getUsername_thenReturnUserEmail() {
        assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    void getAuthorities_thenReturnListOfScopeValue() {
        var authorities = userDetails.getAuthorities();
        var authorityNames = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        var expectedAuthorityNames = new HashSet<>(Collections.singletonList(Scope.SCOPE_READ.getName()));

        assertEquals(expectedAuthorityNames, authorityNames);
    }


}