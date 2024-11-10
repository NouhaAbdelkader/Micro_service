package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableDiscoveryClient
@CrossOrigin(origins = "http://localhost:4200")

public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder  builder) {
        return builder.routes()

                .route("condidatNaj", r->r.path("/candidat/**")
                        .uri("http://localhost:8089/"))//candidat:8080


                .route("Evaluation",r->r.path("/evaluation/**")
                        .uri("http://localhost:8084/"))



                .route("Event",r->r.path("/event/**")
                        .uri("http://localhost:8088/"))

                .route("Forum",r->r.path("/forum/**")
                        .uri("http://forumA:8082/"))
                .route("User",r->r.path("/api/auth/**")
                        .uri("http://gestionUser:4000/"))
                .route("Lms",r->r.path("/api/modules/**")
                        .uri("http://gestionLMS:4005/"))

                .route("Project",r->r.path("/project/**")
                        .uri("http://localhost:8085/"))

                .route("Recrutement",r->r.path("/recrutement/**")
                        .uri("http://localhost:8087/"))
                .route("Calendar",r->r.path("/**")
                  .uri("http://calendar:8086/calendar/"))

                .build();


    }
}

