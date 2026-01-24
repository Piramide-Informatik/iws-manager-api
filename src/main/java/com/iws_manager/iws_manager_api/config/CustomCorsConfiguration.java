package com.iws_manager.iws_manager_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CustomCorsConfiguration {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // FRONTEND ORIGINS
        config.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "http://localhost:8081",
                "https://iws-manager.web.app",
                "https://iws.piramide.de"));

        // ALLOWED METHODS
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ALLOWED HEADERS
        config.setAllowedHeaders(List.of("*"));

        // CRITICAL FOR SESSIONS
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}