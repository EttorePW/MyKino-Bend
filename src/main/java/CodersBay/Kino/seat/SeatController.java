package CodersBay.Kino.seat;

import CodersBay.Kino.seat.dtos.Request.NewSeatDTO;
import CodersBay.Kino.seat.dtos.Respond.RespSeatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<RespSeatDTO>> getAllSeats() {
        return new ResponseEntity<>(seatService.getAllTheSeats(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespSeatDTO> getSeatById(@PathVariable Long id) {
        return new ResponseEntity<>(seatService.getSeatById(id),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeatById(@PathVariable Long id) {
        return new ResponseEntity<>(seatService.deleteSeatById(id),HttpStatus.OK);
    }
}
