package com.akerke.authserver.domain.model;

import com.akerke.authserver.common.constants.SecurityRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "user_")
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"otp", "username", "accountNonExpired", "authorities", "enabled", "credentialsNonExpired", "accountNonLocked"})
public class User implements UserDetails {

    @Id
    private String id;

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private Boolean emailConfirmed;
    private Integer otp;
    private LocalDateTime registeredTime;
    private LocalDateTime otpCreationTime;

    private List<SecurityRole> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.name())))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
