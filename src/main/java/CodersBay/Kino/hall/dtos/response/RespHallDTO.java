package CodersBay.Kino.hall.dtos.response;

import CodersBay.Kino.cinema.dtos.response.RespCinemaDTO;
import CodersBay.Kino.enums.MovieVersion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RespHallDTO {

    private String hallId;
    private int capacity;
    private int occupiedSeats;
    private MovieVersion supportedMovieVersion;
    private double seatPrice;
    private RespCinemaDTO cinemaDTO;
    private List<String> screeningTimes = new ArrayList<>();

}
