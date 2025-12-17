package org.nom_entreprise.ms_launcher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Bibliothèque")
                        .version("1.0.0")
                        .description("API pour la gestion de la bibliothèque - Architecture monolithique prête pour microservices")
                        .contact(new Contact()
                                .name("Nom Entreprise")
                                .email("contact@nom-entreprise.com")));
    }

    @Bean
    public GroupedOpenApi borrowApi() {
        return GroupedOpenApi.builder()
                .group("borrow")
                .displayName("Module Borrow - Gestion des emprunts et réservations")
                .packagesToScan("org.nom_entreprise.borrow_controller.controller")
                .pathsToMatch("/api/holds/**")
                .build();
    }

    @Bean
    public GroupedOpenApi catalogApi() {
        return GroupedOpenApi.builder()
                .group("catalog")
                .displayName("Module Catalog - Gestion du catalogue de livres")
                .packagesToScan("org.nom_entreprise.catalog_controller.controller")
                .pathsToMatch("/api/books/**")
                .build();
    }
}
