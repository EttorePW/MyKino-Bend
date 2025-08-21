package CodersBay.Kino.hall;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallScreeningTimeService {

    private final DataSource dataSource;

    /**
     * Obtiene los horarios de proyección para una sala específica
     * desde la tabla hall_screening_times
     *
     * @param hallId ID de la sala
     * @return Lista de horarios de proyección
     */
    public List<String> getScreeningTimes(Long hallId) {
        List<String> screeningTimes = new ArrayList<>();
        
        String sql = "SELECT screening_time FROM hall_screening_times WHERE hall_id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, hallId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    screeningTimes.add(rs.getString("screening_time"));
                }
            }
            
        } catch (SQLException e) {
            // Log the error
            System.err.println("Error getting screening times for hall " + hallId + ": " + e.getMessage());
        }
        
        return screeningTimes;
    }
    
    /**
     * Inserta manualmente los horarios de proyección en la tabla hall_screening_times
     * Este método se usa como fallback si @ElementCollection no funciona
     *
     * @param hallId ID de la sala
     * @param screeningTimes Lista de horarios a insertar
     * @return número de filas insertadas
     */
    public int insertScreeningTimes(Long hallId, List<String> screeningTimes) {
        if (screeningTimes == null || screeningTimes.isEmpty()) {
            return 0;
        }
        
        String sql = "INSERT INTO hall_screening_times (hall_id, screening_time) VALUES (?, ?)";
        int totalInserted = 0;
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (String screeningTime : screeningTimes) {
                if (screeningTime != null && !screeningTime.trim().isEmpty()) {
                    pstmt.setLong(1, hallId);
                    pstmt.setString(2, screeningTime.trim());
                    pstmt.addBatch();
                }
            }
            
            int[] results = pstmt.executeBatch();
            for (int result : results) {
                if (result > 0) {
                    totalInserted++;
                }
            }
            
            System.out.println("Successfully inserted " + totalInserted + " screening times for hall " + hallId);
            
        } catch (SQLException e) {
            System.err.println("Error inserting screening times for hall " + hallId + ": " + e.getMessage());
        }
        
        return totalInserted;
    }
    
    /**
     * Verifica si una sala tiene horarios de proyección registrados
     *
     * @param hallId ID de la sala
     * @return true si tiene horarios, false si no
     */
    public boolean hasScreeningTimes(Long hallId) {
        String sql = "SELECT COUNT(*) FROM hall_screening_times WHERE hall_id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, hallId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking screening times for hall " + hallId + ": " + e.getMessage());
        }
        
        return false;
    }
}
