package com.example.productservice.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfiguration {

        @Value("${jwt-variables.KEY}")
        private String jwtSecret;

        @Bean
        public SecretKey secretKey(){
            return Keys.hmacShaKeyFor(jwtSecret.getBytes());
        }
    }

