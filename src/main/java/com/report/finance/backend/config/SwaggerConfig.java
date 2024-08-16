package com.report.finance.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(
           new Info()
                .title("Backend Report Finance")
                .description("Report Finance API")
                .contact(
                    new Contact()
                    .email("fadelrahmatullah97@gmail.com")
                    .name("Fadel Rahmatullah"))
                
        );
    }
}
