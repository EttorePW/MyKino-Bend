package CodersBay.Kino.customer.dtos.Request;

import CodersBay.Kino.seat.dtos.Request.NewSeatDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewCustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private boolean anAdult;
    private List<NewSeatDTO> seats = new ArrayList<>();
}
