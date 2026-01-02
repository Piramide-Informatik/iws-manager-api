package com.iws_manager.iws_manager_api.config;

import org.springframework.context.annotation.Configuration;

@Configuration
@io.swagger.v3.oas.annotations.OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "IWS Manager API", version = "1.0"), security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth"))
@io.swagger.v3.oas.annotations.security.SecurityScheme(name = "bearerAuth", type = io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {
    // Las anotaciones de arriba configurarán todo automáticamente
}
