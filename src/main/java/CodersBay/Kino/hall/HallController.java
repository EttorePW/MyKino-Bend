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
    private final HallScreeningTimeService hallScreeningTimeService;

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
    
    @GetMapping("/{id}/screening-times/debug")
    public ResponseEntity<java.util.Map<String, Object>> debugScreeningTimes(@PathVariable long id) {
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        
        try {
            // Verificar si existen screening times en la base de datos
            boolean hasScreeningTimes = hallScreeningTimeService.hasScreeningTimes((long)id);
            java.util.List<String> screeningTimes = hallScreeningTimeService.getScreeningTimes((long)id);
            
            result.put("hallId", id);
            result.put("hasScreeningTimes", hasScreeningTimes);
            result.put("screeningTimesCount", screeningTimes.size());
            result.put("screeningTimes", screeningTimes);
            result.put("status", "success");
            
        } catch (Exception e) {
            result.put("error", e.getMessage());
            result.put("status", "error");
        }
        
        return new ResponseEntity<>(result, HttpStatus.OK);
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
