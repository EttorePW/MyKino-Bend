package CodersBay.Kino.bills;

import CodersBay.Kino.customer.dtos.Request.NewCustomerDTO;
import CodersBay.Kino.customer.dtos.Respond.RespCustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewBillDTO {

    private String customerName;
    private double totalPrice;
    private String billDate;
    private NewCustomerDTO customer;

}
