package CodersBay.Kino.bills;

import CodersBay.Kino.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;
    private String customerName;
    private double totalPrice;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate billDate;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
