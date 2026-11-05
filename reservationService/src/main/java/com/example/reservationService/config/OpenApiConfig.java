package com.example.reservationService.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

public class OpenApiConfig {
    @Bean
    public OpenAPI reservationOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Reservation Service API")
                        .description("REST API for managing restaurant reservations")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Reservation Service Team")
                        )
                );
    }
}
