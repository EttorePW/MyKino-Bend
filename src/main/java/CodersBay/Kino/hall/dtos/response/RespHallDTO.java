package CodersBay.Kino.hall.dtos.response;

import CodersBay.Kino.cinema.Cinema;
import CodersBay.Kino.cinema.dtos.response.RespCinemaDTO;
import CodersBay.Kino.enums.MovieVersion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private Long hallId;
    private int capacity;
    private int occupiedSeats;
    @Enumerated(EnumType.STRING)
    private MovieVersion supportedMovieVersion;
    private double seatPrice;
    private RespCinemaDTO cinemaDTO;
    private List<String> screeningTimes = new ArrayList<>();

}
