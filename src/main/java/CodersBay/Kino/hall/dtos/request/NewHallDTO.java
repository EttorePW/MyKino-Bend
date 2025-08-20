package CodersBay.Kino.hall.dtos.request;

import CodersBay.Kino.enums.MovieVersion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private String screeningTimes; // Horarios como texto separado por comas

    // Métodos de conveniencia para compatibilidad con frontend
    public List<String> getScreeningTimesAsList() {
        if (screeningTimes == null || screeningTimes.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(screeningTimes.split(","))
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public void setScreeningTimesFromList(List<String> times) {
        if (times == null || times.isEmpty()) {
            this.screeningTimes = "";
        } else {
            this.screeningTimes = String.join(",", times);
        }
    }

}
