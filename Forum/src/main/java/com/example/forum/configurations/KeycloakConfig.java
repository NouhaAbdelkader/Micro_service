package com.example.forum.configurations;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl("http://keycloak:8180/auth")
                .realm("MicroProject")
                .clientId("Forum")
                .clientSecret("L9OZv9XYN44dhvoDMY1OZMxDKxlErqFs")
                .username("najiba")
                .password("admin123")
                .build();

    }


}
