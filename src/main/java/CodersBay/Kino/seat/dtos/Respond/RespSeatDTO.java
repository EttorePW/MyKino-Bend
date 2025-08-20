package CodersBay.Kino.seat.dtos.Respond;

import CodersBay.Kino.enums.MovieVersion;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RespSeatDTO {
    private Long seatId;
    private String cinemaName;
    private int hallId;
    private int movieId;
    private MovieVersion movieVersion;
    private String movieName;
    private int colNumber;
    private int rowNumber;
    private String reservationDate;
    private String reservationTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate premieredAt;
}
