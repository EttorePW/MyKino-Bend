package CodersBay.Kino.movie;

import CodersBay.Kino.controllerExceptionhandler.customExeption.CheckPropertiesException;
import CodersBay.Kino.controllerExceptionhandler.customExeption.MovieVersionIsNotSupported;
import CodersBay.Kino.controllerExceptionhandler.customExeption.NotFoundException;
import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.hall.Hall;
import CodersBay.Kino.hall.HallRepository;
import CodersBay.Kino.hall.HallService;
import CodersBay.Kino.hall.dtos.response.RespHallDTO;
import CodersBay.Kino.cinema.dtos.response.RespCinemaDTO;
import CodersBay.Kino.movie.dtos.request.NewMovieDTO;
import CodersBay.Kino.movie.dtos.response.RespMovieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final HallService hallService;
    private final HallRepository hallRepository;


    public List<RespMovieDTO> getAllMovies() {
        List<Movie> movieList = movieRepository.findAll();
        List<RespMovieDTO> respMovieDTOList = new ArrayList<>();
        movieList.forEach(movie -> respMovieDTOList.add(convertToRespMovieDTO(movie)));
        return respMovieDTOList;
    }
    
    public int getMovieCount() {
        return (int) movieRepository.count();
    }

    public RespMovieDTO createNewPost(NewMovieDTO newMovieDTO) throws IOException {
        try {
            System.out.println("Creating new movie: " + newMovieDTO.getTitle());
            System.out.println("Movie version: " + newMovieDTO.getMovieVersion());
            System.out.println("Halls requested: " + newMovieDTO.getHalls());
            
            if (controllMethod(newMovieDTO)) {
                throw new CheckPropertiesException("Please check the syntax from your properties, you may have an error ");
            }
            RespMovieDTO result = convertToRespMovieDTO(putMovieInList(newMovieDTO));
            System.out.println("Movie created successfully with ID: " + result.getMovieId());
            return result;
        } catch (Exception e) {
            System.err.println("Error creating movie: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    private boolean controllMethod(NewMovieDTO newMovieDTO) {
        return newMovieDTO.getTitle() == null
                || newMovieDTO.getTitle().isEmpty()
                || newMovieDTO.getMainCharacter() == null
                || newMovieDTO.getMainCharacter().isEmpty()
                || newMovieDTO.getPremieredAt() == null
                || newMovieDTO.getMovieVersion() == null
                || newMovieDTO.getHalls() == null
                || newMovieDTO.getHalls().isEmpty()
                || newMovieDTO.getImage() == null
                || newMovieDTO.getImage().isEmpty()
                || newMovieDTO.getImageBkd() == null
                || newMovieDTO.getImageBkd().isEmpty()
                || newMovieDTO.getVideoId() == null
                || newMovieDTO.getVideoId().isEmpty();
    }
    public Movie putMovieInList(NewMovieDTO newMovieDTO) throws IOException {
        List<Hall> hallsList = hallRepository.findByHallIdIn(newMovieDTO.getHalls());
        if (hallsList.isEmpty()) {
            throw new MovieVersionIsNotSupported("No Halls found by this ID");
        }
        
        // Create movie with embedded halls
        Movie movie = new Movie(newMovieDTO.getTitle(), newMovieDTO.getMainCharacter(), 
            newMovieDTO.getDescription(), convertDateToLocalDate(newMovieDTO.getPremieredAt()), 
            newMovieDTO.getMovieVersion(), newMovieDTO.getImage(), newMovieDTO.getImageBkd(), 
            newMovieDTO.getVideoId());
        
        // Add compatible halls to the movie
        for (Hall hall : hallsList) {
            if(hall.getSupportedMovieVersion().equals(newMovieDTO.getMovieVersion())){
                Movie.MovieHall movieHall = new Movie.MovieHall(
                    hall.getHallId().hashCode(), // Use hashCode for consistent ID generation
                    hall.getCinemaName(),
                    hall.getCinemaAddress(),
                    hall.getCapacity(),
                    hall.getOccupiedSeats(),
                    hall.getSupportedMovieVersion(),
                    hall.getSeatPrice(),
                    hall.getScreeningTimes()
                );
                movie.getHalls().add(movieHall);
            }
        }
        
        if(movie.getHalls().isEmpty()) {
            throw new MovieVersionIsNotSupported("Movie version is not supported by any hall");
        }
        
        movieRepository.save(movie);
        return movie;
    }
    public LocalDate convertDateToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }

    public RespMovieDTO convertToRespMovieDTO(Movie movie) {
        return new RespMovieDTO(movie.getMovieId(), movie.getTitle(), movie.getMainCharacter(), 
            movie.getDescription(), movie.getPremieredAt(), movie.getMovieVersion(), 
            convertMovieHallsToRespHallDTOs(movie.getHalls()), movie.getImage(), 
            movie.getImageBkd(), movie.getVideoId());
    }

    // Convert embedded MovieHalls to RespHallDTOs
    private List<RespHallDTO> convertMovieHallsToRespHallDTOs(List<Movie.MovieHall> movieHalls) {
        List<RespHallDTO> hallDTOs = new ArrayList<>();
        for (Movie.MovieHall movieHall : movieHalls) {
            RespCinemaDTO cinemaDTO = new RespCinemaDTO("", movieHall.getCinemaName(), movieHall.getCinemaAddress(), "", 0);
            RespHallDTO hallDTO = new RespHallDTO(
                String.valueOf(movieHall.getHallId()),
                movieHall.getCapacity(),
                movieHall.getOccupiedSeats(),
                movieHall.getSupportedMovieVersion(),
                movieHall.getSeatPrice(),
                cinemaDTO,
                movieHall.getScreeningTimes()
            );
            hallDTOs.add(hallDTO);
        }
        return hallDTOs;
    }

    public List<RespMovieDTO> getMoviesByMovieVersion(String movieVersion) {
        List<Movie> moviesByVersion = movieRepository.findByMovieVersion(MovieVersion.valueOf(movieVersion));
        List<RespMovieDTO> respMovieDTOList = new ArrayList<>();
        moviesByVersion.forEach(movie -> respMovieDTOList.add(convertToRespMovieDTO(movie)));
        return respMovieDTOList;
    }
    
    public RespMovieDTO getMovieById(String movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> 
            new NotFoundException("Movie not found, please enter a correct ID", "/api/movies/" + movieId));
        return convertToRespMovieDTO(movie);
    }

    public RespMovieDTO insertMovieInToHall(String movieId, String hallId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> 
            new NotFoundException("Movie not found", "/api/movies/" + movieId));
        Hall hall = hallRepository.findById(hallId).orElseThrow(() -> 
            new NotFoundException("Hall not found", "/api/halls/" + hallId));

        if(hall.getSupportedMovieVersion().equals(movie.getMovieVersion())){
            // Add the hall to the movie if not already present
            boolean hallExists = movie.getHalls().stream()
                .anyMatch(h -> h.getHallId().equals((long) hallId.hashCode()));
            
            if (!hallExists) {
                Movie.MovieHall movieHall = new Movie.MovieHall(
                    hallId.hashCode(), // Use hashCode for consistent ID generation
                    hall.getCinemaName(),
                    hall.getCinemaAddress(), 
                    hall.getCapacity(),
                    hall.getOccupiedSeats(),
                    hall.getSupportedMovieVersion(),
                    hall.getSeatPrice(),
                    hall.getScreeningTimes()
                );
                movie.getHalls().add(movieHall);
                movieRepository.save(movie);
            }
            return convertToRespMovieDTO(movie);
        }
        throw new MovieVersionIsNotSupported("Hall does not support movie version");
    }

    public ResponseEntity<String> deleteMovie(String id) {
        movieRepository.deleteById(id);
        return new ResponseEntity<>("Deleted the movie successfully", HttpStatus.OK);
    }

    public List<RespMovieDTO> getMoviesByHallId(String hallId) {
        List<Movie> movies = movieRepository.findAll();
        List<RespMovieDTO> filteredMovieList = new ArrayList<>();
        
        long hallIdHash = hallId.hashCode();

        for (Movie movie : movies) {
            boolean hasHall = movie.getHalls().stream()
                .anyMatch(hall -> hall.getHallId().equals(hallIdHash));
            if (hasHall) {
                filteredMovieList.add(convertToRespMovieDTO(movie));
            }
        }
        return filteredMovieList;
    }
}
