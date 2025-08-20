package CodersBay.Kino.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DatabaseDebugController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-info")
    public Map<String, Object> getDatabaseInfo() {
        Map<String, Object> info = new HashMap<>();
        
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            
            info.put("database", metaData.getDatabaseProductName());
            info.put("version", metaData.getDatabaseProductVersion());
            info.put("url", metaData.getURL());
            info.put("username", metaData.getUserName());
            
            // Get tables
            List<String> tables = new ArrayList<>();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            info.put("tables", tables);
            
        } catch (Exception e) {
            info.put("error", e.getMessage());
        }
        
        return info;
    }

    @GetMapping("/movie-count")
    public Map<String, Object> getMovieCount() {
        Map<String, Object> result = new HashMap<>();
        
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) as count FROM movie";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            if (rs.next()) {
                result.put("movieCount", rs.getInt("count"));
            }
            
            // Get movie structure
            sql = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = 'movie' ORDER BY ordinal_position";
            rs = conn.prepareStatement(sql).executeQuery();
            List<Map<String, String>> columns = new ArrayList<>();
            while (rs.next()) {
                Map<String, String> col = new HashMap<>();
                col.put("name", rs.getString("column_name"));
                col.put("type", rs.getString("data_type"));
                columns.add(col);
            }
            result.put("movieColumns", columns);
            
        } catch (Exception e) {
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @GetMapping("/hall-migration-status")
    public Map<String, Object> getHallMigrationStatus() {
        Map<String, Object> result = new HashMap<>();
        
        try (Connection conn = dataSource.getConnection()) {
            // Check if hall table has screening_times column
            String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'hall' AND column_name = 'screening_times')";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            if (rs.next()) {
                result.put("hall_has_screening_times_column", rs.getBoolean(1));
            }
            
            // Check if hall_screening_times table exists
            sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'hall_screening_times')";
            rs = conn.prepareStatement(sql).executeQuery();
            if (rs.next()) {
                result.put("hall_screening_times_table_exists", rs.getBoolean(1));
            }
            
            // Get hall table structure
            sql = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = 'hall' ORDER BY ordinal_position";
            rs = conn.prepareStatement(sql).executeQuery();
            List<Map<String, String>> hallColumns = new ArrayList<>();
            while (rs.next()) {
                Map<String, String> col = new HashMap<>();
                col.put("name", rs.getString("column_name"));
                col.put("type", rs.getString("data_type"));
                hallColumns.add(col);
            }
            result.put("hallColumns", hallColumns);
            
            // Count data in both tables
            sql = "SELECT COUNT(*) as count FROM hall";
            rs = conn.prepareStatement(sql).executeQuery();
            if (rs.next()) {
                result.put("hallCount", rs.getInt("count"));
            }
            
            // Check hall_screening_times if exists
            sql = "SELECT COUNT(*) as count FROM hall_screening_times";
            try {
                rs = conn.prepareStatement(sql).executeQuery();
                if (rs.next()) {
                    result.put("screeningTimesCount", rs.getInt("count"));
                }
            } catch (Exception e) {
                result.put("screeningTimesError", e.getMessage());
            }
            
        } catch (Exception e) {
            result.put("error", e.getMessage());
        }
        
        return result;
    }
}
