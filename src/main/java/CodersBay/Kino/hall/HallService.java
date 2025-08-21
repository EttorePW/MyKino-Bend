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
        if (controllMethod(newHallDTO)) {
            throw new CheckPropertiesException("Please check the syntax from your properties, you may have an error ");
        }

        Cinema cinema = cinemaRepository.findById(newHallDTO.getCinemaId()).orElseThrow(() -> new NotFoundException("Cinema not found, please enter an correct ID","/api/cinema/"+newHallDTO.getCinemaId()));
        if(cinema.getHallsList().toArray().length < cinema.getMaxHalls() || cinema.getHallsList().isEmpty()) {
            // Crear el hall con los datos básicos primero
            Hall hall = new Hall(newHallDTO.getCapacity(), newHallDTO.getOccupiedSeats(), newHallDTO.getSupportedMovieVersion(),newHallDTO.getSeatPrice(),cinemaRepository.findById(newHallDTO.getCinemaId()).orElseThrow(()-> new NotFoundException("Cinema could not been found","/api/cinema"+newHallDTO.getCinemaId())),newHallDTO.getScreeningTimes());
            
            // Log para debugging
            logger.info("Creating hall with {} screening times: {}", 
                       newHallDTO.getScreeningTimes() != null ? newHallDTO.getScreeningTimes().size() : 0,
                       newHallDTO.getScreeningTimes());
            
            // Guardar el hall PRIMERO sin los screeningTimes para obtener el ID
            Hall savedHall = hallRepository.save(hall);
            logger.info("Hall saved with ID: {}", savedHall.getHallId());
            
            // SIEMPRE insertar screening times manualmente para asegurar que se guarden
            if (newHallDTO.getScreeningTimes() != null && !newHallDTO.getScreeningTimes().isEmpty()) {
                logger.info("Inserting {} screening times manually for hall {}: {}", 
                           newHallDTO.getScreeningTimes().size(), 
                           savedHall.getHallId(),
                           newHallDTO.getScreeningTimes());
                           
                int inserted = hallScreeningTimeService.insertScreeningTimes(
                    savedHall.getHallId(), 
                    newHallDTO.getScreeningTimes()
                );
                
                if (inserted > 0) {
                    logger.info("✅ Successfully inserted {} screening times for hall {}", inserted, savedHall.getHallId());
                } else {
                    logger.error("❌ Failed to insert screening times for hall {}", savedHall.getHallId());
                }
                
                // Verificar que se insertaron correctamente
                boolean hasScreeningTimes = hallScreeningTimeService.hasScreeningTimes(savedHall.getHallId());
                logger.info("Verification: Hall {} has screening times: {}", savedHall.getHallId(), hasScreeningTimes);
            }
            
            return convertToRespHallDTO(savedHall);
        }
        throw new IsNotPossibleBeacauseMaxHallsIsReached("The cinema has already to many Halls");

    }
    private boolean controllMethod(NewHallDTO newHallDTO) {
        if (newHallDTO.getCapacity() == 0
                || newHallDTO.getSupportedMovieVersion() == null
                || newHallDTO.getCinemaId() == 0 ){
            return true;
        }
        return false;
    }

    public RespHallDTO convertToRespHallDTO(Hall hall) {
        // Obtener screeningTimes desde la base de datos usando el nuevo servicio
        List<String> screeningTimes = new ArrayList<>();
        try {
            screeningTimes = hallScreeningTimeService.getScreeningTimes(hall.getHallId());
            logger.info("Fetched {} screening times for hall {}", screeningTimes.size(), hall.getHallId());
        } catch (Exception e) {
            logger.error("Error fetching screening times for hall {}: {}", hall.getHallId(), e.getMessage());
        }
        
        return RespHallDTO.builder()
                .hallId(hall.getHallId())
                .capacity(hall.getCapacity())
                .occupiedSeats(hall.getOccupiedSeats())
                .supportedMovieVersion(hall.getSupportedMovieVersion())
                .seatPrice(hall.getSeatPrice())
                .cinemaDTO(convertToRespCinemaDTO(hall.getCinema()))
                .screeningTimes(screeningTimes) // Usar los datos obtenidos de la base de datos
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
