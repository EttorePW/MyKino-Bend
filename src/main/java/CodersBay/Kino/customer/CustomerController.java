package CodersBay.Kino.customer;

import CodersBay.Kino.customer.dtos.Request.NewCustomerDTO;
import CodersBay.Kino.customer.dtos.Respond.RespCustomerDTO;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<RespCustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RespCustomerDTO> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<RespCustomerDTO> postCustomer(@RequestBody NewCustomerDTO newCustomerDTO) {
        return new ResponseEntity<>(customerService.convertToDTO(customerService.createCustomer(newCustomerDTO)),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.deleteById(id),HttpStatus.OK);
    }
}
