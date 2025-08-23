package CodersBay.Kino.bills;

import CodersBay.Kino.customer.dtos.Respond.RespCustomerDTO;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RespBillDTO {

    private String billId;
    private String customerName;
    private double totalPrice;
    private String billDate;
    private RespCustomerDTO customer;

}
