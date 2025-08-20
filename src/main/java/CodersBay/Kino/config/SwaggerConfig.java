package CodersBay.Kino.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kino")
                        .description("Dokumentation der KINO REST API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ettore Junior Pesendorfer-Wagner")
                                .email("ettore.pw@gmail.com")
                                .url("https://ettorepesendorfer.netlify.app"))
                )
                .servers(List.of(new Server().url("http://localhost:8080").description("Lokale Umgebung")))
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("openai-public")
                .pathsToMatch("/api/**")
                .build();
    }
}
