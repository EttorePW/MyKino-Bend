package CodersBay.Kino.hall;

import CodersBay.Kino.hall.dtos.request.NewHallDTO;
import CodersBay.Kino.hall.dtos.request.UpdatedHallDTO;
import CodersBay.Kino.hall.dtos.response.RespHallDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hall")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @GetMapping("/{id}")
    public ResponseEntity<RespHallDTO> getHall(@PathVariable long id) {
        return new ResponseEntity<>(hallService.getHallDTOById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RespHallDTO>> getHalls() {
        return new ResponseEntity<>(hallService.getAllHaals(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RespHallDTO> postNewHall(@RequestBody NewHallDTO newHallDTO) {
        return new ResponseEntity<>(hallService.createNewHall(newHallDTO),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespHallDTO> putHall(@PathVariable long id, @RequestBody UpdatedHallDTO updatedHallDTO) {
        return new ResponseEntity<>(hallService.updateHall(id,updatedHallDTO),HttpStatus.OK) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable long id) {
        return new ResponseEntity<>(hallService.deleteHall(id),HttpStatus.OK);
    }
}
