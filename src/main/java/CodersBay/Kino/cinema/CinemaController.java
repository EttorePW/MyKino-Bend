package CodersBay.Kino.cinema;

import CodersBay.Kino.cinema.dtos.request.NewCinemaDTO;
import CodersBay.Kino.cinema.dtos.response.CheckCinemaDTO;
import CodersBay.Kino.cinema.dtos.response.RespCinemaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinema")
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping("/{id}")
    public ResponseEntity<CheckCinemaDTO> getCinemaDTOById(@PathVariable String id){
        return new ResponseEntity<>(cinemaService.getCinemaDTOById(id),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CheckCinemaDTO>> getCinemaDTOs(){
        return new ResponseEntity<>(cinemaService.getAllCinemas(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<RespCinemaDTO> postCinema(@RequestBody(required = false) NewCinemaDTO newCinemaDTO) {

        return new ResponseEntity<>(cinemaService.creatNewCinema(newCinemaDTO),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCinemaById(@PathVariable String id) {
        return new ResponseEntity<>(cinemaService.deleteCinema(id),HttpStatus.OK);
    }
}
