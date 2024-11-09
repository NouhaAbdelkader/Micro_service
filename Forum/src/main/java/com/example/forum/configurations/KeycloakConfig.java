package com.example.forum.configurations;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class KeycloakConfig {
    private static volatile Keycloak keycloak;
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean


    public static Keycloak getInstance() {
        if (keycloak == null) {
            synchronized (Keycloak.class) {
                if (keycloak == null) {
                    keycloak = KeycloakBuilder.builder()
                            .serverUrl("http://keycloak:8180")
                            .realm("MicroProject")
                            .grantType(OAuth2Constants.PASSWORD)
                            .clientId("Forum")
                            .clientSecret("xUcwn5vtCMnpNKHSbS1rmUdO8jV7yQV7")
                            .username("nouha")
                            .password("admin123")
                            .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
                            .build();
                }
            }
        }
        return keycloak;
    }

    public List<String> getAllUsers() {
        try {
            Keycloak keycloak = getInstance();
            //log.info("Fetching users from realm: MicroProject");
            List<UserRepresentation> users = keycloak.realm("MicroProject").users().list();
            //log.info("Users fetched: " + users);
            return users.stream()
                    .map(UserRepresentation::getUsername)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            //log.error("Error fetching users", e);
            return List.of();
        }
    }
    /**
      * Creates a Keycloak instance for the Forum application.
      *
      * @return a Keycloak instance configured for the Forum application
      */
/*    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl("http://keycloak:8180")  // Remove /auth from the end
                .realm("MicroProject")
                .clientId("Forum")
                .clientSecret("h0uM6ehhnX2wyrFa7b7qtlvafvEexXjJ")
                .username("nouha")
                .password("admin123")
                .build();
    }*/


}
