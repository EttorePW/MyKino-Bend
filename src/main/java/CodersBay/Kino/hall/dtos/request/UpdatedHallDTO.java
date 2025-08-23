package CodersBay.Kino.hall.dtos.request;

import CodersBay.Kino.enums.MovieVersion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdatedHallDTO {

    private int capacity;
    private int occupiedSeats;
    private MovieVersion supportedMovieVersion;

}