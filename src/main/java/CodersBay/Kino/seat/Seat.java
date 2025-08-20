package CodersBay.Kino.seat;


import CodersBay.Kino.customer.Customer;
import CodersBay.Kino.enums.MovieVersion;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    private String cinemaName;
    private int hallId;
    private int movieId;
    @Enumerated(EnumType.STRING)
    private MovieVersion movieVersion;
    private String movieName;
    private int colNumber;
    private int rowNumber;
    private String reservationDate;
    private String reservationTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate premieredAt;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
