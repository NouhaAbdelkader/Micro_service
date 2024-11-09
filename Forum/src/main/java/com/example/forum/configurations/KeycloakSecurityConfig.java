package com.example.forum.configurations;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;


import java.util.stream.Collectors;
import java.util.stream.Stream;


import java.util.Arrays;

//@KeycloakConfiguration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
public class KeycloakSecurityConfig {

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("forum/questions/Teacher/**").hasAuthority("Teacher")
                        .requestMatchers("forum/questions/Student/**").hasAuthority("Student")
                        .requestMatchers("forum/questions/Admin/**").hasAuthority("Admin")
                        .requestMatchers("forum/questions/shared/**").hasAnyAuthority("Teacher", "Student")
                        //answers
                        .requestMatchers("forum/answers/Teacher/**").hasAuthority("Teacher")
                        .requestMatchers("forum/answers/Student/**").hasAuthority("Student")
                        .requestMatchers("forum/answers/Admin/**").hasAuthority("Admin")
                        .requestMatchers("forum/answers/shared/**").hasAnyAuthority("Teacher", "Student")
                        //votes
                        .requestMatchers("forum/ForumVote/Teacher/**").hasAuthority("Teacher")
                        .requestMatchers("forum/ForumVote/Student/**").hasAuthority("Student")
                        .requestMatchers("forum/ForumVote/Admin/**").hasAuthority("Admin")
                        .requestMatchers("forum/ForumVote/shared/**").hasAnyAuthority("Teacher", "Student")
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }





    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }


    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        return jwt -> {
            JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            defaultGrantedAuthoritiesConverter.setAuthorityPrefix("");

            Collection<GrantedAuthority> authorities = new ArrayList<>();

            // Extraction correcte des rôles depuis resource_access
            if (jwt.getClaim("resource_access") != null) {
                Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
                if (resourceAccess.containsKey("Forum")) {
                    Map<String, Object> forum = (Map<String, Object>) resourceAccess.get("Forum");
                    if (forum.containsKey("roles")) {
                        List<String> roles = (List<String>) forum.get("roles");
                        roles.stream()
                                .map(SimpleGrantedAuthority::new)
                                .forEach(authorities::add);
                    }
                }
            }

            return authorities;
        };
    }




    @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }
