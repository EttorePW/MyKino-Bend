package CodersBay.Kino.bills;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Document(collection = "bills")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bill {

    @Id
    private String billId;
    private String customerId; // Reference to customer ID
    private String customerName;
    private double totalPrice;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate billDate;

}
