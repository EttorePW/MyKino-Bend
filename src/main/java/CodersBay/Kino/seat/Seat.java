package CodersBay.Kino.seat;


import CodersBay.Kino.enums.MovieVersion;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection = "seats")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    private String seatId;
    private String cinemaName;
    private String hallId;
    private String movieId;
    private MovieVersion movieVersion;
    private String movieName;
    private int colNumber;
    private int rowNumber;
    private String reservationDate;
    private String reservationTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate premieredAt;
    private String customerId; // Reference to customer ID

}
