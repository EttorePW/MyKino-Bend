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
            System.out.println("[DEBUG] No screening times to insert for hall " + hallId);
            return 0;
        }
        
        System.out.println("[DEBUG] Starting insertion of " + screeningTimes.size() + " screening times for hall " + hallId);
        System.out.println("[DEBUG] Screening times to insert: " + screeningTimes);
        
        // Primero limpiar screening times existentes para este hall
        String deleteSql = "DELETE FROM hall_screening_times WHERE hall_id = ?";
        String insertSql = "INSERT INTO hall_screening_times (hall_id, screening_time) VALUES (?, ?)";
        int totalInserted = 0;
        
        try (Connection conn = dataSource.getConnection()) {
            // Auto-commit en false para manejar transacción manualmente
            conn.setAutoCommit(false);
            
            try {
                // Limpiar screening times existentes
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setLong(1, hallId);
                    int deletedRows = deleteStmt.executeUpdate();
                    System.out.println("[DEBUG] Deleted " + deletedRows + " existing screening times for hall " + hallId);
                }
                
                // Insertar nuevos screening times
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    for (String screeningTime : screeningTimes) {
                        if (screeningTime != null && !screeningTime.trim().isEmpty()) {
                            insertStmt.setLong(1, hallId);
                            insertStmt.setString(2, screeningTime.trim());
                            insertStmt.addBatch();
                            System.out.println("[DEBUG] Added to batch: hall_id=" + hallId + ", screening_time='" + screeningTime.trim() + "'");
                        }
                    }
                    
                    int[] results = insertStmt.executeBatch();
                    for (int result : results) {
                        if (result > 0) {
                            totalInserted++;
                        }
                    }
                }
                
                // Commit la transacción
                conn.commit();
                System.out.println("[SUCCESS] ✅ Successfully inserted " + totalInserted + " screening times for hall " + hallId);
                
            } catch (SQLException e) {
                // Rollback en caso de error
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
            
        } catch (SQLException e) {
            System.err.println("[ERROR] ❌ Error inserting screening times for hall " + hallId + ": " + e.getMessage());
            e.printStackTrace();
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
