package com.example.membercertification.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {
    @Bean
    public PasswordEncoder passwordEncoder() { // PasswordEncoder 빈을 정의
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
