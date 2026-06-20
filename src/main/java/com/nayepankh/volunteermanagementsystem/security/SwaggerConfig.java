package com.nayepankh.volunteermanagementsystem.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title       = "NGO Volunteer Management API",
                version     = "1.0.0",
                description = "REST API for managing NGO volunteers with JWT authentication",
                contact     = @Contact(
                        name  = "NGO Tech Team",
                        email = "tech@ngo.org"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name   = "bearerAuth",
        type   = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    // All config via annotations — no bean needed with springdoc
}