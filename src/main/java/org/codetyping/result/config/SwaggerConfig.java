package org.codetyping.result.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        final String secSchemeName = "bearerAuth";
        return new OpenAPI().info(
                new Info().title("Results Service API").version("1.0")
        ).addSecurityItem(
                new SecurityRequirement().addList(secSchemeName)
        ).components(new Components()
                .addSecuritySchemes(secSchemeName,
                        new SecurityScheme()
                                .name(secSchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}