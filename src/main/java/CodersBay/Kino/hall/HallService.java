package CodersBay.Kino.hall;

import CodersBay.Kino.cinema.Cinema;
import CodersBay.Kino.cinema.CinemaRepository;
import CodersBay.Kino.cinema.CinemaService;
import CodersBay.Kino.cinema.dtos.request.NewCinemaDTO;
import CodersBay.Kino.cinema.dtos.response.RespCinemaDTO;
import CodersBay.Kino.controllerExceptionhandler.customExeption.*;
import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.hall.dtos.request.NewHallDTO;
import CodersBay.Kino.hall.dtos.request.UpdatedHallDTO;
import CodersBay.Kino.hall.dtos.response.RespHallDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallService {
    private static final Logger logger = LoggerFactory.getLogger(HallService.class);

    private final HallRepository hallRepository;
    private final CinemaRepository cinemaRepository;
    private final HallScreeningTimeService hallScreeningTimeService;


    @Transactional
    public RespHallDTO createNewHall(NewHallDTO newHallDTO) {
        try {
            logger.info("Starting to create hall with DTO: {}", newHallDTO);
            
            if (controllMethod(newHallDTO)) {
                logger.error("DTO validation failed for: {}", newHallDTO);
                throw new CheckPropertiesException("Please check the syntax from your properties, you may have an error ");
            }

            logger.info("Looking for cinema with ID: {}", newHallDTO.getCinemaId());
            Cinema cinema = cinemaRepository.findById(newHallDTO.getCinemaId())
                .orElseThrow(() -> {
                    logger.error("Cinema not found with ID: {}", newHallDTO.getCinemaId());
                    return new NotFoundException("Cinema not found, please enter an correct ID","/api/cinema/"+newHallDTO.getCinemaId());
                });
            
            logger.info("Cinema found: {} with {} halls (max: {})", 
                       cinema.getName(), 
                       cinema.getHallsList().size(), 
                       cinema.getMaxHalls());
            
            if(cinema.getHallsList().size() < cinema.getMaxHalls() || cinema.getHallsList().isEmpty()) {
                logger.info("Creating hall with capacity: {}, price: {}, version: {}", 
                           newHallDTO.getCapacity(), 
                           newHallDTO.getSeatPrice(), 
                           newHallDTO.getSupportedMovieVersion());
                
                // Crear el hall con los datos básicos
                Hall hall = new Hall(
                    newHallDTO.getCapacity(), 
                    newHallDTO.getOccupiedSeats(), 
                    newHallDTO.getSupportedMovieVersion(),
                    newHallDTO.getSeatPrice(),
                    cinema,
                    newHallDTO.getScreeningTimes()
                );
                
                logger.info("Hall created in memory with {} screening times", 
                           hall.getScreeningTimes() != null ? hall.getScreeningTimes().size() : 0);
                
                // Guardar el hall
                logger.info("Saving hall to database...");
                Hall savedHall = hallRepository.save(hall);
                logger.info("✅ Hall saved with ID: {}", savedHall.getHallId());
                
                // Convertir a DTO
                logger.info("Converting hall to response DTO...");
                RespHallDTO respHallDTO = convertToRespHallDTO(savedHall);
                logger.info("✅ Hall DTO created successfully");
                
                return respHallDTO;
            } else {
                logger.error("Cinema {} already has maximum halls ({}/{})", 
                           cinema.getName(), cinema.getHallsList().size(), cinema.getMaxHalls());
                throw new IsNotPossibleBeacauseMaxHallsIsReached("The cinema has already too many Halls");
            }
        } catch (Exception e) {
            logger.error("Error creating hall: {}", e.getMessage(), e);
            throw e; // Re-throw to let Spring handle it
        }
    }
    private boolean controllMethod(NewHallDTO newHallDTO) {
        if (newHallDTO == null) {
            logger.error("NewHallDTO is null");
            return true;
        }
        
        if (newHallDTO.getCapacity() <= 0) {
            logger.error("Invalid capacity: {}", newHallDTO.getCapacity());
            return true;
        }
        
        if (newHallDTO.getSupportedMovieVersion() == null) {
            logger.error("MovieVersion is null");
            return true;
        }
        
        if (newHallDTO.getCinemaId() == null || newHallDTO.getCinemaId() <= 0) {
            logger.error("Invalid cinemaId: {}", newHallDTO.getCinemaId());
            return true;
        }
        
        if (newHallDTO.getSeatPrice() < 0) {
            logger.error("Invalid seat price: {}", newHallDTO.getSeatPrice());
            return true;
        }
        
        return false;
    }

    public RespHallDTO convertToRespHallDTO(Hall hall) {
        // Ahora usamos directamente los screening times de la entidad (columna JSON)
        List<String> screeningTimes = hall.getScreeningTimes() != null ? 
                                     new ArrayList<>(hall.getScreeningTimes()) : 
                                     new ArrayList<>();
        
        logger.info("Converting hall {} with {} screening times: {}", 
                   hall.getHallId(), screeningTimes.size(), screeningTimes);
        
        return RespHallDTO.builder()
                .hallId(hall.getHallId())
                .capacity(hall.getCapacity())
                .occupiedSeats(hall.getOccupiedSeats())
                .supportedMovieVersion(hall.getSupportedMovieVersion())
                .seatPrice(hall.getSeatPrice())
                .cinemaDTO(convertToRespCinemaDTO(hall.getCinema()))
                .screeningTimes(screeningTimes) // Usar directamente de la entidad
                .build();
    }

    private RespCinemaDTO convertToRespCinemaDTO(Cinema cinema) {
        return new RespCinemaDTO(cinema.getCinemaId(), cinema.getName(), cinema.getAddress(), cinema.getManager(), cinema.getMaxHalls());
    }

    public List<RespHallDTO> convertToRespHallDTOList(List<Hall> halls) {
        List<RespHallDTO> respHallDTOList = new ArrayList<>();
        halls.forEach(hall -> {respHallDTOList.add(convertToRespHallDTO(hall));});
        return respHallDTOList;
    }

    public RespHallDTO getHallDTOById(long id) {
        return convertToRespHallDTO(hallRepository.findById(id).orElseThrow(() -> new NotFoundException("Hall not found, please enter an correct ID","/api/cinema/"+id)));
    }
    public Hall getHallById(long id) {
        return hallRepository.findById(id).orElseThrow(() -> new NotFoundException("Hall not found, please enter an correct ID","/api/cinema/"+id));
    }
    public List<RespHallDTO> getAllHaals() {
        List<RespHallDTO> respHallDTOList = new ArrayList<>();
        List<Hall> halls = hallRepository.findAll();
        halls.forEach(hall -> {respHallDTOList.add(convertToRespHallDTO(hall));});
        return respHallDTOList;
    }

    public RespHallDTO updateHall(Long id, UpdatedHallDTO updatedHallDTO) {

        Hall hall = hallRepository.findById(id).orElseThrow(() -> new NotFoundException("Hall not found, please enter an correct ID","/api/hall/"+id));
        if(hall.getMoviePlaysInList().isEmpty()){
            if(updatedHallDTO.getSupportedMovieVersion().equals(hall.getSupportedMovieVersion())){
                hall.setCapacity(updatedHallDTO.getCapacity());
                hall.setOccupiedSeats(updatedHallDTO.getOccupiedSeats());
                hall.setSupportedMovieVersion(updatedHallDTO.getSupportedMovieVersion());
                hallRepository.save(hall);
                return convertToRespHallDTO(hall);
            }
            if(updatedHallDTO.getSupportedMovieVersion().equals(MovieVersion.R3D) && hall.getSupportedMovieVersion().equals(MovieVersion.DBOX)  ) {
                hall.setCapacity(updatedHallDTO.getCapacity());
                hall.setOccupiedSeats(updatedHallDTO.getOccupiedSeats());
                hall.setSupportedMovieVersion(updatedHallDTO.getSupportedMovieVersion());
                hallRepository.save(hall);
                return convertToRespHallDTO(hall);
            }
            throw new CanNotChangeVersion("It is not possible to change the hall's version, only DBOX to R3D");
        }
            throw new NotPossibleBecauseThereAreSomeMovies("There are still some movies, please remove these before updating the hall");

    }
    public ResponseEntity<String> deleteHall(Long id) {
        hallRepository.deleteById(id);
        return new ResponseEntity<>("Deleted the hall successfully",HttpStatus.OK);
    }

}
