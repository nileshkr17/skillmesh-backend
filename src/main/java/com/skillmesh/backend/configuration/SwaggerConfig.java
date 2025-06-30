package com.skillmesh.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI skillMeshOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SkillMesh API")
                .description("Real-time Matchmaking Engine")
                .version("v1.0"));
    }
}
