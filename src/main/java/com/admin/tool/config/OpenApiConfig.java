package com.admin.tool.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Admin Tool API",
                version = "1.0.0",
                description = "Spring Boot 기반 운영툴 템플릿 API 문서",
                contact = @Contact(
                        name = "Admin Team",
                        email = "admin@example.com"
                )
        ),
        servers = {
                @Server(
                        description = "Local Server",
                        url = "http://localhost:8080/api"
                )
        }
)
@SecurityScheme(
        name = "bearer-auth",
        description = "JWT 인증",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
