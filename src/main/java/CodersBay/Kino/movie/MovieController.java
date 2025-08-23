package CodersBay.Kino.movie;

import CodersBay.Kino.hall.HallService;
import CodersBay.Kino.movie.dtos.request.NewMovieDTO;
import CodersBay.Kino.movie.dtos.response.RespMovieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final HallService hallService;


    @GetMapping
    public ResponseEntity<List<RespMovieDTO>> getMovies(){
        try {
            System.out.println("=== DEBUG: MovieController.getMovies() called ===");
            List<RespMovieDTO> movies = movieService.getAllMovies();
            System.out.println("=== DEBUG: Retrieved " + movies.size() + " movies ===");
            
            if (movies.isEmpty()) {
                System.out.println("=== DEBUG: No movies found - checking database connection ===");
            } else {
                System.out.println("=== DEBUG: First movie: " + movies.get(0).getTitle() + " ===");
            }
            
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("=== DEBUG ERROR: Exception in getMovies(): " + e.getMessage() + " ===");
            System.out.println("=== DEBUG ERROR: Exception class: " + e.getClass().getSimpleName() + " ===");
            e.printStackTrace();
            // Return empty list on error instead of 500
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @GetMapping("/{movieVersion}")
    public ResponseEntity<List<RespMovieDTO>> getMoviesByVersion(@PathVariable String movieVersion){
        return new ResponseEntity<>(movieService.getMoviesByMovieVersion(movieVersion), HttpStatus.OK);
    }
    @GetMapping("/halls/{hallId}")
    public ResponseEntity<List<RespMovieDTO>> getMoviesByHall(@PathVariable String hallId){
        return new ResponseEntity<>(movieService.getMoviesByHallId(hallId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RespMovieDTO> postNewMovie(@RequestBody NewMovieDTO newMovieDTO) throws IOException {
        return new ResponseEntity<>(movieService.createNewPost(newMovieDTO), HttpStatus.CREATED);
    }
    @PostMapping("/{movieId}/hall/{hallId}")
    public ResponseEntity<RespMovieDTO> postHallToMovie(@PathVariable String movieId, @PathVariable String hallId){
      return new ResponseEntity<>(movieService.insertMovieInToHall(movieId, hallId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable String id){
        return new ResponseEntity<>(movieService.deleteMovie(id),HttpStatus.OK);
    }
    
    @GetMapping("/debug/count")
    public ResponseEntity<String> getMovieCount(){
        try {
            long count = movieService.getMovieCount();
            return new ResponseEntity<>("Total movies in database: " + count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error connecting to database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
