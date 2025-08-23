package CodersBay.Kino.hall.dtos.request;

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
public class NewHallDTO {

    private int capacity;
    private int occupiedSeats;
    private MovieVersion supportedMovieVersion;
    private double seatPrice;
    private String cinemaId;
    private List<String> screeningTimes = new ArrayList<>();



}
