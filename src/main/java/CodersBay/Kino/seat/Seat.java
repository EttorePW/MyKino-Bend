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
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatId;
    
    @Column(name = "cinema_name")
    private String cinemaName;
    
    @Column(name = "hall_id")
    private int hallId;
    
    @Column(name = "movie_id")
    private int movieId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "movie_version")
    private MovieVersion movieVersion;
    
    @Column(name = "movie_name")
    private String movieName;
    
    @Column(name = "col_number")
    private int colNumber;
    
    @Column(name = "row_number")
    private int rowNumber;
    
    @Column(name = "reservation_date")
    private String reservationDate;
    
    @Column(name = "reservation_time")
    private String reservationTime;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "premiered_at")
    private LocalDate premieredAt;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
