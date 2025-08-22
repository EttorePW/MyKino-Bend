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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallService {

    private final HallRepository hallRepository;
    private final CinemaRepository cinemaRepository;


    public RespHallDTO createNewHall(NewHallDTO newHallDTO) {
        if (controllMethod(newHallDTO)) {
            throw new CheckPropertiesException("Please check the syntax from your properties, you may have an error ");
        }

        Cinema cinema = cinemaRepository.findById(newHallDTO.getCinemaId()).orElseThrow(() -> new NotFoundException("Cinema not found, please enter an correct ID","/api/cinema/"+newHallDTO.getCinemaId()));
        if(cinema.getHallsList().toArray().length < cinema.getMaxHalls() || cinema.getHallsList().isEmpty()) {
            Hall hall = new Hall(newHallDTO.getCapacity(), newHallDTO.getOccupiedSeats(), newHallDTO.getSupportedMovieVersion(),newHallDTO.getSeatPrice(),cinemaRepository.findById(newHallDTO.getCinemaId()).orElseThrow(()-> new NotFoundException("Cinema could not been found","/api/cinema"+newHallDTO.getCinemaId())),newHallDTO.getScreeningTimes());
            hallRepository.save(hall);
            return convertToRespHallDTO(hall);
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
        return new RespHallDTO(hall.getHallId(),hall.getCapacity(),hall.getOccupiedSeats(),hall.getSupportedMovieVersion(), hall.getSeatPrice(), convertToRespCinemaDTO(hall.getCinema()),hall.getScreeningTimes());
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
