package CodersBay.Kino.cinema;

import CodersBay.Kino.cinema.dtos.request.NewCinemaDTO;
import CodersBay.Kino.cinema.dtos.response.CheckCinemaDTO;
import CodersBay.Kino.cinema.dtos.response.RespCinemaDTO;
import CodersBay.Kino.controllerExceptionhandler.customExeption.CheckPropertiesException;
import CodersBay.Kino.controllerExceptionhandler.customExeption.NotFoundException;
import CodersBay.Kino.controllerExceptionhandler.customExeption.NotPossibleBecauseThereAreSomeMovies;
import CodersBay.Kino.hall.Hall;
import CodersBay.Kino.hall.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    private final HallService hallService;

    public RespCinemaDTO creatNewCinema(NewCinemaDTO newCinemaDTO) {
        if (controllMethod(newCinemaDTO)) {
             throw new CheckPropertiesException("Please check the syntax from your properties, you may have an error ");
        }

        Cinema cinema = new Cinema(newCinemaDTO.getName(), newCinemaDTO.getAddress(), newCinemaDTO.getManager(), newCinemaDTO.getMaxHalls());
        cinemaRepository.save(cinema);
        return convertToRespCinemaDTO(cinema);
    }

    private boolean controllMethod(NewCinemaDTO newCinemaDTO) {
        if (newCinemaDTO.getName() == null
                || newCinemaDTO.getName().equals("")
                || newCinemaDTO.getAddress() == null
                || newCinemaDTO.getAddress().equals("")
                || newCinemaDTO.getManager() == null
                || newCinemaDTO.getManager().equals("")
                || newCinemaDTO.getMaxHalls() == 0) {
            return true;
        }
        return false;
    }

    public RespCinemaDTO convertToRespCinemaDTO(Cinema cinema) {
        return new RespCinemaDTO(cinema.getCinemaId(), cinema.getName(), cinema.getAddress(), cinema.getManager(), cinema.getMaxHalls());
    }
    public CheckCinemaDTO convertToCheckCinemaDTO(Cinema cinema) {
        // Get halls for this cinema from the hall repository
        List<Hall> halls = hallService.getHallsByCinemaId(cinema.getCinemaId());
        return new CheckCinemaDTO(cinema.getCinemaId(), cinema.getName(), cinema.getAddress(), cinema.getManager(), cinema.getMaxHalls(), hallService.convertToRespHallDTOList(halls));
    }

    public CheckCinemaDTO getCinemaDTOById(String id) {
       return convertToCheckCinemaDTO(cinemaRepository.findById(id).orElseThrow(() -> new NotFoundException("Cinema not found, please enter an correct ID","/api/cinema/"+id))) ;
    }
//    public Cinema getCinema(long id) {
//        return cinemaRepository.findById(id).get();
//    }
//    public boolean checkIfCinemaHasMaxHalls(long id) {
//        Cinema cinema = getCinema(id);
//        if (cinema.getMaxHalls() == cinema.getHallsList().toArray().length) {
//            return false;
//        }
//        else{
//            return true;
//        }
//    }

    public List<CheckCinemaDTO> getAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        List<CheckCinemaDTO> cinemasDTOs = new ArrayList<>();
        cinemas.forEach(cinema -> {cinemasDTOs.add(convertToCheckCinemaDTO(cinema));});
        return cinemasDTOs;
    }

    public ResponseEntity<String> deleteCinema(String id) {
        try {
            System.out.println("Attempting to delete cinema with ID: " + id);
            
            Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new NotFoundException("Cinema not found, please enter an correct ID","/api/cinema/"+id));
            System.out.println("Found cinema: " + cinema.getName());
            
            List<Hall> halls = hallService.getHallsByCinemaId(id);
            System.out.println("Found " + halls.size() + " halls to delete");
            
            // Delete all halls belonging to this cinema
            for(Hall hall : halls){
                System.out.println("Deleting hall with ID: " + hall.getHallId());
                hallService.deleteHall(hall.getHallId());
            }
            
            cinemaRepository.deleteById(id);
            System.out.println("Cinema deleted successfully: " + id);
            
            return new ResponseEntity<>("Deleted cinema successfully", HttpStatus.OK);
        } catch (NotFoundException e) {
            System.err.println("Cinema not found: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting cinema: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete cinema: " + e.getMessage(), e);
        }
    }
}
