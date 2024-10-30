package com.biblestudy.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Define the security filter chain for URL-based access
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection with the new method
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow frames from the same origin
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").permitAll() // Allow public access to certain endpoints
                        .requestMatchers("/study/**").permitAll()
                        .requestMatchers("/invitation/**").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/session").permitAll()
                        .requestMatchers("/topic/bibleStudyUpdates/**").permitAll()
                        .requestMatchers("/updateBibleStudy/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().permitAll() // All other requests require authentication
                );

        return http.build();
    }

    // Define the password encoder as a bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
