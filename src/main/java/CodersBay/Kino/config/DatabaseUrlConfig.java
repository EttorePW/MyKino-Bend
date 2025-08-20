package CodersBay.Kino.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("prod")
public class DatabaseUrlConfig {

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            // Render provides postgres:// but we need postgresql://
            databaseUrl = databaseUrl.replace("postgres://", "postgresql://");
        }
        
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            URI dbUri = new URI(databaseUrl);
            
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            
            // Extract username and password
            String[] userInfo = dbUri.getUserInfo().split(":");
            String username = userInfo[0];
            String password = userInfo.length > 1 ? userInfo[1] : "";
            
            return DataSourceBuilder
                    .create()
                    .url(dbUrl + "?sslmode=require")
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        }
        
        // Fallback para desarrollo local
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://localhost:5432/kinodb")
                .username("postgres")
                .password("password")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
