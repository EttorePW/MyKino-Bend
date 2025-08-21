package CodersBay.Kino.hall.dtos.request;

import CodersBay.Kino.enums.MovieVersion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewHallDTO {

    private int capacity;
    private int occupiedSeats;
    private MovieVersion supportedMovieVersion;
    private double seatPrice;
    private Long cinemaId;
    
    // Directamente una lista de strings como espera el frontend
    @Builder.Default
    private List<String> screeningTimes = new ArrayList<>();

}
