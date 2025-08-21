package CodersBay.Kino.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "https://mykinoapp.netlify.app",  // Frontend de producción
                    "http://localhost:3000",         // React dev server
                    "http://localhost:5173",         // Vite dev server  
                    "http://localhost:5174",         // Vite dev server alternate
                    "http://localhost:5175"          // Vite dev server alternate
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
