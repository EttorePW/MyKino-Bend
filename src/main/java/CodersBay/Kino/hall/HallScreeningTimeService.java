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
}
