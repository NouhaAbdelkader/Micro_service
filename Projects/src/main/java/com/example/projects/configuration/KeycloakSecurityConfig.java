package com.example.projects.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class KeycloakSecurityConfig {

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF explicitly
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/projects/user/*").hasAnyAuthority("user")
                        .requestMatchers("/projects/admin/**").hasAnyAuthority("admin")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(customAuthoritiesExtractor());
        return jwtAuthenticationConverter;
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> customAuthoritiesExtractor() {
        return new Converter<Jwt, Collection<GrantedAuthority>>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt jwt) {
                JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                defaultGrantedAuthoritiesConverter.setAuthorityPrefix(""); // No prefix for authorities

                Collection<GrantedAuthority> authorities = new ArrayList<>(defaultGrantedAuthoritiesConverter.convert(jwt));

                // Extract roles from resource_access
                Map<String, Object> claims = jwt.getClaims();
                if (claims != null) {
                    Object resourceAccessObject = claims.get("resource_access");
                    if (resourceAccessObject instanceof Map) {
                        Map<String, Object> resourceAccess = (Map<String, Object>) resourceAccessObject;
                        Object projectsObject = resourceAccess.get("projects-service");
                        if (projectsObject instanceof Map) {
                            Map<String, Object> projects = (Map<String, Object>) projectsObject;
                            Object rolesObject = projects.get("roles");
                            if (rolesObject instanceof List) {
                                List<String> roles = (List<String>) rolesObject;
                                roles.stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .forEach(authorities::add);
                            }
                        }
                    }
                }

                return authorities;
            }
        };
    }
}
