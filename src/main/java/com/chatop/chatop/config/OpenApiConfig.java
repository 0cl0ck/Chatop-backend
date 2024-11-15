package com.chatop.chatop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    
    @Value("${server.port:8005}")
    private String serverPort;
    
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("Utiliser le token JWT obtenu via /api/auth/login");

        Schema<?> loginRequestSchema = new Schema<>()
            .type("object");
        Map<String, Schema> loginProperties = new HashMap<>();
        loginProperties.put("email", new Schema<>().type("string").example("user@example.com"));
        loginProperties.put("password", new Schema<>().type("string").example("password123"));
        loginRequestSchema.setProperties(loginProperties);

        Schema<?> registerRequestSchema = new Schema<>()
            .type("object");
        Map<String, Schema> registerProperties = new HashMap<>();
        registerProperties.put("email", new Schema<>().type("string").example("user@example.com"));
        registerProperties.put("password", new Schema<>().type("string").example("password123"));
        registerProperties.put("fullName", new Schema<>().type("string").example("John Doe"));
        registerRequestSchema.setProperties(registerProperties);

        Schema<?> messageRequestSchema = new Schema<>()
            .type("object");
        Map<String, Schema> messageProperties = new HashMap<>();
        messageProperties.put("rental_id", new Schema<>().type("integer").example(1));
        messageProperties.put("message", new Schema<>().type("string").example("Je suis intéressé par cette location"));
        messageRequestSchema.setProperties(messageProperties);

        return new OpenAPI()
            .servers(Arrays.asList(
                new Server().url("http://localhost:" + serverPort)
                    .description("Serveur de développement")
            ))
            .info(new Info()
                .title("API Chatop")
                .version("1.0.0")
                .description("API de mise en relation entre locataires et propriétaires")
                .contact(new Contact()
                    .name("Chatop Support")
                    .email("support@chatop.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", securityScheme)
                .addSchemas("LoginRequest", loginRequestSchema)
                .addSchemas("RegisterRequest", registerRequestSchema)
                .addSchemas("MessageRequest", messageRequestSchema))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}