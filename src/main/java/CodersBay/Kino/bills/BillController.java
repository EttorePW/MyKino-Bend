package CodersBay.Kino.bills;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bill")
public class BillController {
    private final BillService billService;

    @PostMapping
    public ResponseEntity<RespBillDTO> createBill(@RequestBody NewBillDTO newBillDTO) throws MessagingException {
        return new ResponseEntity<>(billService.createNewBill(newBillDTO),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespBillDTO> getBillById(@PathVariable String id) {
        return new ResponseEntity<>(billService.convertBillToBillDTO(billService.getBillById(id)),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RespBillDTO>> getAllBills() {
        return new ResponseEntity<>(billService.getAll(),HttpStatus.OK);
    }

}
