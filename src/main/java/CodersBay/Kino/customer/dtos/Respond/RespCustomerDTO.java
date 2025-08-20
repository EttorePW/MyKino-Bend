package CodersBay.Kino.customer.dtos.Respond;

import CodersBay.Kino.bills.RespBillDTO;
import CodersBay.Kino.seat.dtos.Respond.RespSeatDTO;
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
public class RespCustomerDTO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private boolean anAdult;
    private List<RespSeatDTO> seats = new ArrayList<>();
}
