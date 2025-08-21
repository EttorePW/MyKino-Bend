package CodersBay.Kino.debug;

import CodersBay.Kino.cinema.CinemaRepository;
import CodersBay.Kino.hall.Hall;
import CodersBay.Kino.hall.HallRepository;
import CodersBay.Kino.hall.dtos.request.NewHallDTO;
import CodersBay.Kino.enums.MovieVersion;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/debug/hall")
@RequiredArgsConstructor
public class HallDebugController {
    
    private static final Logger logger = LoggerFactory.getLogger(HallDebugController.class);
    
    private final HallRepository hallRepository;
    private final CinemaRepository cinemaRepository;

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testHallCreation() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. Test database connection
            long cinemaCount = cinemaRepository.count();
            long hallCount = hallRepository.count();
            
            result.put("cinemaCount", cinemaCount);
            result.put("hallCount", hallCount);
            result.put("databaseConnection", "OK");
            
            // 2. Test creating a minimal hall object
            if (cinemaCount > 0) {
                var cinema = cinemaRepository.findAll().get(0);
                result.put("testCinema", cinema.getName());
                
                // Create a test hall without saving
                List<String> testScreeningTimes = new ArrayList<>();
                testScreeningTimes.add("10:00");
                testScreeningTimes.add("14:00");
                
                Hall testHall = new Hall(
                    100, // capacity
                    0,   // occupiedSeats
                    MovieVersion.D2D, // supportedMovieVersion
                    12.50, // seatPrice
                    cinema,
                    testScreeningTimes
                );
                
                result.put("testHallCreation", "SUCCESS - Hall created in memory");
                result.put("testHallCapacity", testHall.getCapacity());
                result.put("testHallScreeningTimes", testHall.getScreeningTimes());
                
                // Try to save the test hall
                try {
                    Hall savedHall = hallRepository.save(testHall);
                    result.put("testHallSave", "SUCCESS - Hall saved with ID: " + savedHall.getHallId());
                    
                    // Clean up - delete the test hall
                    hallRepository.delete(savedHall);
                    result.put("cleanup", "SUCCESS - Test hall deleted");
                    
                } catch (Exception saveException) {
                    result.put("testHallSave", "FAILED");
                    result.put("saveError", saveException.getMessage());
                    result.put("saveErrorType", saveException.getClass().getSimpleName());
                }
                
            } else {
                result.put("error", "No cinemas found in database");
            }
            
        } catch (Exception e) {
            logger.error("Debug test failed: ", e);
            result.put("error", e.getMessage());
            result.put("errorType", e.getClass().getSimpleName());
        }
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/test-create")
    public ResponseEntity<Map<String, Object>> testCreateWithDTO(@RequestBody NewHallDTO newHallDTO) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            logger.info("Testing hall creation with DTO: {}", newHallDTO);
            
            // Log all DTO properties
            result.put("receivedDTO", Map.of(
                "capacity", newHallDTO.getCapacity(),
                "occupiedSeats", newHallDTO.getOccupiedSeats(),
                "supportedMovieVersion", newHallDTO.getSupportedMovieVersion(),
                "seatPrice", newHallDTO.getSeatPrice(),
                "cinemaId", newHallDTO.getCinemaId(),
                "screeningTimes", newHallDTO.getScreeningTimes()
            ));
            
            // Test cinema lookup
            if (newHallDTO.getCinemaId() != null) {
                var cinema = cinemaRepository.findById(newHallDTO.getCinemaId());
                if (cinema.isPresent()) {
                    result.put("cinemaFound", true);
                    result.put("cinemaName", cinema.get().getName());
                    result.put("cinemaMaxHalls", cinema.get().getMaxHalls());
                    result.put("cinemaCurrentHalls", cinema.get().getHallsList().size());
                } else {
                    result.put("cinemaFound", false);
                    result.put("error", "Cinema not found with ID: " + newHallDTO.getCinemaId());
                }
            } else {
                result.put("error", "Cinema ID is null");
            }
            
            result.put("status", "Test completed");
            
        } catch (Exception e) {
            logger.error("Debug DTO test failed: ", e);
            result.put("error", e.getMessage());
            result.put("errorType", e.getClass().getSimpleName());
            result.put("stackTrace", e.getStackTrace()[0].toString());
        }
        
        return ResponseEntity.ok(result);
    }
}
