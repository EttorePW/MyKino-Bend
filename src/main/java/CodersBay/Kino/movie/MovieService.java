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
        System.out.println("Searching for halls with IDs: " + newMovieDTO.getHalls());
        System.out.println("Hall IDs count: " + newMovieDTO.getHalls().size());
        
        List<Hall> hallsList = hallRepository.findByHallIdIn(newMovieDTO.getHalls());
        System.out.println("Found halls: " + hallsList.size());
        
        if (!hallsList.isEmpty()) {
            System.out.println("First found hall ID: " + hallsList.get(0).getHallId());
            System.out.println("First found hall cinema: " + hallsList.get(0).getCinemaName());
            System.out.println("First found hall version: " + hallsList.get(0).getSupportedMovieVersion());
        }
        
        if (hallsList.isEmpty()) {
            System.err.println("No halls found in database for IDs: " + newMovieDTO.getHalls());
            // Let's also check what halls exist in the database
            List<Hall> allHalls = hallRepository.findAll();
            System.err.println("Total halls in database: " + allHalls.size());
            if (!allHalls.isEmpty()) {
                System.err.println("First few hall IDs in database: ");
                allHalls.stream().limit(5).forEach(hall -> 
                    System.err.println("  - " + hall.getHallId() + " (" + hall.getCinemaName() + ")"));
            }
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
                    (long) hall.getHallId().hashCode(), // Convert int to Long
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
        try {
            System.out.println("Adding movie " + movieId + " to hall " + hallId);
            
            Movie movie = movieRepository.findById(movieId).orElseThrow(() -> 
                new NotFoundException("Movie not found", "/api/movies/" + movieId));
            Hall hall = hallRepository.findById(hallId).orElseThrow(() -> 
                new NotFoundException("Hall not found", "/api/halls/" + hallId));

            System.out.println("Found movie: " + movie.getTitle() + " (version: " + movie.getMovieVersion() + ")");
            System.out.println("Found hall: " + hall.getHallId() + " (supports: " + hall.getSupportedMovieVersion() + ")");

            if(hall.getSupportedMovieVersion().equals(movie.getMovieVersion())){
                // Add the hall to the movie if not already present
                long hallHashId = (long) hallId.hashCode();
                boolean hallExists = movie.getHalls().stream()
                    .anyMatch(h -> h.getHallId().equals(hallHashId));
                
                System.out.println("Hall hash ID: " + hallHashId + ", hall exists: " + hallExists);
                
                if (!hallExists) {
                    Movie.MovieHall movieHall = new Movie.MovieHall(
                        hallHashId, // Convert int to Long
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
                    System.out.println("Movie hall added successfully. Movie now has " + movie.getHalls().size() + " halls");
                } else {
                    System.out.println("Hall already exists in movie");
                }
                return convertToRespMovieDTO(movie);
            }
            System.err.println("Movie version " + movie.getMovieVersion() + " not supported by hall " + hall.getSupportedMovieVersion());
            throw new MovieVersionIsNotSupported("Hall does not support movie version");
        } catch (NotFoundException | MovieVersionIsNotSupported e) {
            System.err.println("Error in insertMovieInToHall: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error in insertMovieInToHall: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to add movie to hall: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<String> deleteMovie(String id) {
        try {
            System.out.println("Attempting to delete movie with ID: " + id);
            
            // Check if movie exists
            Movie movie = movieRepository.findById(id).orElseThrow(() -> 
                new NotFoundException("Movie not found, please enter a correct ID", "/api/movies/" + id));
            
            System.out.println("Found movie: " + movie.getTitle() + " with " + movie.getHalls().size() + " halls");
            
            movieRepository.deleteById(id);
            System.out.println("Movie deleted successfully: " + id);
            
            return new ResponseEntity<>("Deleted the movie successfully", HttpStatus.OK);
        } catch (NotFoundException e) {
            System.err.println("Movie not found for deletion: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting movie: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete movie: " + e.getMessage(), e);
        }
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
