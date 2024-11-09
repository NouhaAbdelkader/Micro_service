package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("Evaluation",r->r.path("/evaluation/**")
                        .uri("http://localhost:8084/"))

                .route("Calender",r->r.path("/calender/**")
                        .uri("http://localhost:8086/"))

                .route("Event",r->r.path("/event/**")
                        .uri("http://localhost:8088/"))

                .route("Forum",r->r.path("/forum/**")
                        .uri("http://localhost:8082/"))

                .route("Project",r->r.path("/project/**")
                        .uri("http://localhost:8085/"))

                .route("User",r->r.path("/api/auth/**")
                        .uri("http://localhost:4000/"))
                .route("Recrutement",r->r.path("/recrutement/**")
                        .uri("http://localhost:8087/"))
                .build();
    }
}
