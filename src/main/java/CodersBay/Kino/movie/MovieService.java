package CodersBay.Kino.movie;

import CodersBay.Kino.controllerExceptionhandler.customExeption.CheckPropertiesException;
import CodersBay.Kino.controllerExceptionhandler.customExeption.MovieVersionIsNotSupported;
import CodersBay.Kino.controllerExceptionhandler.customExeption.NotFoundException;
import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.hall.Hall;
import CodersBay.Kino.hall.HallRepository;
import CodersBay.Kino.hall.HallService;
import CodersBay.Kino.hall.dtos.request.NewHallDTO;
import CodersBay.Kino.hall.dtos.response.RespHallDTO;
import CodersBay.Kino.movie.dtos.request.NewMovieDTO;
import CodersBay.Kino.movie.dtos.response.RespMovieDTO;
import CodersBay.Kino.pk.MoviePlaysInRepository;
import CodersBay.Kino.pk.Movie_plays_in;
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
    private final MoviePlaysInRepository moviePlaysInRepository;


    public List<RespMovieDTO> getAllMovies() {
        // Use simple query to avoid PostgreSQL issues
        List<Movie> movieList = movieRepository.findAll();
        List<RespMovieDTO> respMovieDTOList = new ArrayList<>();
        movieList.forEach(movie -> respMovieDTOList.add(convertToRespMovieDTOSimple(movie)));
        return respMovieDTOList;
    }

    public RespMovieDTO createNewPost(NewMovieDTO newMovieDTO) throws IOException {
        if (controllMethod(newMovieDTO)) {
            throw new CheckPropertiesException("Please check the syntax from your properties, you may have an error ");
        }
        return convertToRespMovieDTO(putMovieInList(newMovieDTO));
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
        for (Hall hall : hallsList) {
            if(hall.getSupportedMovieVersion().equals(newMovieDTO.getMovieVersion())){
                Movie movie = new Movie(newMovieDTO.getTitle(), newMovieDTO.getMainCharacter(),newMovieDTO.getDescription(),convertDateToLocalDate(newMovieDTO.getPremieredAt()) ,newMovieDTO.getMovieVersion(), newMovieDTO.getImage(), newMovieDTO.getImageBkd(), newMovieDTO.getVideoId());
                movieRepository.save(movie);
                Movie_plays_in mpl = new Movie_plays_in();
                mpl.setMovie(movie);
                mpl.setHall(hall);
                moviePlaysInRepository.save(mpl);
                movie.getMoviePlaysInList().add(mpl);
                return movie;
            }
        }
        throw new MovieVersionIsNotSupported("Movie version is not supported");
    }
    public LocalDate convertDateToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }

    public RespMovieDTO convertToRespMovieDTO(Movie movie) {
        return new RespMovieDTO(movie.getMovieId(),movie.getTitle(),movie.getMainCharacter(),movie.getDescription(),movie.getPremieredAt(),movie.getMovieVersion(),getHallsList(movie),movie.getImage(),movie.getImageBkd(),movie.getVideoId());
    }
    
    public RespMovieDTO convertToRespMovieDTOSimple(Movie movie) {
        // Simple conversion without halls to avoid lazy loading issues
        return new RespMovieDTO(movie.getMovieId(),movie.getTitle(),movie.getMainCharacter(),movie.getDescription(),movie.getPremieredAt(),movie.getMovieVersion(),new ArrayList<>(),movie.getImage(),movie.getImageBkd(),movie.getVideoId());
    }

    public List<RespHallDTO> getHallsList(Movie movie) {
        // Since we now use findAllWithHalls(), the moviePlaysInList should be eagerly loaded
        // Check if the list is initialized and not empty
        if (movie.getMoviePlaysInList() != null && !movie.getMoviePlaysInList().isEmpty()) {
            // Use the eagerly loaded data directly
            return movie.getMoviePlaysInList().stream()
                    .map(mpi -> hallService.convertToRespHallDTO(mpi.getHall()))
                    .toList();
        }
        // If for some reason the eager loading didn't work, return empty list to avoid N+1 queries
        System.out.println("Warning: Movie " + movie.getMovieId() + " has no halls loaded. Returning empty list.");
        return new ArrayList<>();
    }

    public List<RespMovieDTO> getMoviesByMovieVersion(String movieVersion) {
        List<Movie> moviesByVersion = movieRepository.getMoviesByMovieVersion(MovieVersion.valueOf(movieVersion));
        List<RespMovieDTO> respMovieDTOList = new ArrayList<>();
        moviesByVersion.forEach(movie -> {respMovieDTOList.add(convertToRespMovieDTO(movie));});
        return respMovieDTOList;
    }
    public RespMovieDTO getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Cinema not found, please enter an correct ID","/api/cinema/"+movieId));
        return convertToRespMovieDTO(movie);
    }

    public RespMovieDTO insertMovieInToHall(Long movieId, Long hallId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Cinema not found, please enter an correct ID","/api/cinema/"+movieId));
        Hall hall = hallRepository.findById(hallId).orElseThrow(() -> new NotFoundException("Hall not found, please enter an correct ID","/api/hall/"+hallId));

        if(hall.getSupportedMovieVersion().equals(movie.getMovieVersion())){
            Movie_plays_in mpl = new Movie_plays_in();
            mpl.setMovie(movie);
            mpl.setHall(hall);
            moviePlaysInRepository.save(mpl);
            hall.getMoviePlaysInList().add(mpl);
           return convertToRespMovieDTO(movie);
        }
        throw new MovieVersionIsNotSupported("Hall does not support movie version");
    }

    public ResponseEntity<String> deleteMovie(Long id) {
        movieRepository.deleteById(id);
       return new ResponseEntity<>("Deleted the movie successfully",HttpStatus.OK) ;
    }

    public List<RespMovieDTO> getMoviesByHallId(Long hallId) {
        List<Movie> movies = movieRepository.findAll();
        List<RespMovieDTO> respMovieDTOList = new ArrayList<>();
        List<RespMovieDTO> filterdMovieList = new ArrayList<>();

        movies.forEach(movie -> {respMovieDTOList.add(convertToRespMovieDTO(movie));});

        for (RespMovieDTO movie : respMovieDTOList) {
            boolean hasHall = movie.getHalls().stream()
                    .anyMatch(hall -> hall.getHallId().equals(hallId));

            if (hasHall) {
                filterdMovieList.add(movie);
            }
        }
        return filterdMovieList;
    }
}
