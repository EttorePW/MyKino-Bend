package CodersBay.Kino.debug;

import CodersBay.Kino.cinema.Cinema;
import CodersBay.Kino.cinema.CinemaRepository;
import CodersBay.Kino.movie.Movie;
import CodersBay.Kino.movie.MovieRepository;
import CodersBay.Kino.hall.Hall;
import CodersBay.Kino.hall.HallRepository;
import CodersBay.Kino.customer.Customer;
import CodersBay.Kino.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DataCheckController {

    @Autowired
    private CinemaRepository cinemaRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private HallRepository hallRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/data-summary")
    public Map<String, Object> getDataSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        try {
            List<Cinema> cinemas = cinemaRepository.findAll();
            List<Movie> movies = movieRepository.findAll();
            List<Hall> halls = hallRepository.findAll();
            List<Customer> customers = customerRepository.findAll();
            
            summary.put("cinemas_count", cinemas.size());
            summary.put("movies_count", movies.size());
            summary.put("halls_count", halls.size());
            summary.put("customers_count", customers.size());
            
            summary.put("cinemas", cinemas);
            summary.put("movies", movies);
            summary.put("halls", halls);
            summary.put("customers", customers);
            
            summary.put("status", "SUCCESS");
            summary.put("message", "Data retrieved successfully from PostgreSQL");
            
        } catch (Exception e) {
            summary.put("status", "ERROR");
            summary.put("message", "Error retrieving data: " + e.getMessage());
            summary.put("error_type", e.getClass().getSimpleName());
        }
        
        return summary;
    }
    
    @GetMapping("/movies-only")
    public Map<String, Object> getMoviesOnly() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<Movie> movies = movieRepository.findAll();
            result.put("count", movies.size());
            result.put("movies", movies);
            result.put("status", "SUCCESS");
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
        }
        
        return result;
    }
    
    @GetMapping("/cinemas-only")
    public Map<String, Object> getCinemasOnly() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<Cinema> cinemas = cinemaRepository.findAll();
            result.put("count", cinemas.size());
            result.put("cinemas", cinemas);
            result.put("status", "SUCCESS");
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
        }
        
        return result;
    }
}
