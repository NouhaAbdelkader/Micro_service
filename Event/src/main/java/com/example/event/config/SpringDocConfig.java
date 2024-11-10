package com.example.event.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(infoAPI());
    }

    public Info infoAPI() {
        return new Info().title("Event Module Service")
                .description("étude de cas")
                .contact(contactAPI())
                .version("2");
    }

    public Contact contactAPI() {
        return new Contact().name("Equipe DevDynasty");
    }
}