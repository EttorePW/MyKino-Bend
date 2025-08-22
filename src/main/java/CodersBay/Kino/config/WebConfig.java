package CodersBay.Kino.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // alle Endpunkte
                .allowedOrigins(
                    "https://mykinoapp.netlify.app",
                    "http://localhost:3000",
                    "http://localhost:5173",
                    "http://localhost:4200"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}