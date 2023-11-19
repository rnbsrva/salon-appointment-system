package com.akerke.authserver.config;

import com.akerke.authserver.common.jwt.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider,
            JwtFilter  jwtFilter
    ) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/role/manage-app-manager").hasRole("APPLICATION_ADMIN")
                            .requestMatchers("/role/manage-admin").hasAnyRole("APPLICATION_ADMIN","APPLICATION_MANAGER")
                            .requestMatchers("/role/manage-manager").hasAnyRole("APPLICATION_ADMIN","ADMIN","APPLICATION_MANAGER")
                            .anyRequest().permitAll();
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) {
        var authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}
