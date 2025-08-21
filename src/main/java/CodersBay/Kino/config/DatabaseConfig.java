package CodersBay.Kino.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "prod")
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        System.out.println("[DEBUG] DATABASE_URL: " + databaseUrl);
        
        // Handle both postgres:// and postgresql:// formats
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            databaseUrl = databaseUrl.replace("postgres://", "postgresql://");
            System.out.println("[DEBUG] Converted postgres:// to postgresql://");
        }
        
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            try {
                URI dbUri = new URI(databaseUrl);
                
                String[] userInfo = dbUri.getUserInfo().split(":");
                String username = userInfo[0];
                String password = userInfo[1];
                
                // Handle port properly - if not specified, use default PostgreSQL port
                int port = dbUri.getPort();
                if (port == -1) {
                    port = 5432;
                }
                
                String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath() + "?sslmode=require";
                
                System.out.println("[DEBUG] Converted JDBC URL: " + jdbcUrl);
                System.out.println("[DEBUG] Username: " + username);
                
                return DataSourceBuilder
                    .create()
                    .driverClassName("org.postgresql.Driver")
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .build();
                    
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to parse DATABASE_URL: " + e.getMessage());
                throw new RuntimeException("Invalid DATABASE_URL format: " + databaseUrl, e);
            }
        }
        
        System.out.println("[DEBUG] Using fallback H2 database");
        // Fallback to H2 for development
        return DataSourceBuilder
            .create()
            .driverClassName("org.h2.Driver")
            .url("jdbc:h2:mem:testdb")
            .username("sa")
            .password("")
            .build();
    }
}
