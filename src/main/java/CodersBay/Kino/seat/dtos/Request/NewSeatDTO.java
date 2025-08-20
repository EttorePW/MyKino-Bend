package CodersBay.Kino.seat.dtos.Request;

import CodersBay.Kino.customer.dtos.Request.NewCustomerDTO;
import CodersBay.Kino.enums.MovieVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewSeatDTO {
    private String cinemaName;
    private int hallId;
    private int movieId;
    private MovieVersion movieVersion;
    private String movieName;
    private int colNumber;
    private int rowNumber;
    private String reservationDate;
    private String reservationTime;
    private String premieredAt;
}
