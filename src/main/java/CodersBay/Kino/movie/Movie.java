package CodersBay.Kino.movie;

import CodersBay.Kino.enums.MovieVersion;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    private String movieId;
    private String title;
    private String mainCharacter;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate premieredAt;
    private MovieVersion movieVersion;
    private String image;
    private String imageBkd;
    private String videoId;

    // Halls embedded directly - no more complex relationships!
    private List<MovieHall> halls = new ArrayList<>();
    
    // Embedded Hall information for simplicity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieHall {
        private Long hallId;
        private String cinemaName;
        private String cinemaAddress;
        private int capacity;
        private int occupiedSeats;
        private MovieVersion supportedMovieVersion;
        private double seatPrice;
        private List<String> screeningTimes = new ArrayList<>();
    }

    public Movie (String title, String mainCharacter, String description, LocalDate premieredAt, MovieVersion movieVersion, String image,String imageBkd, String videoId) {
        this.title = title;
        this.mainCharacter = mainCharacter;
        this.description = description;
        this.premieredAt = premieredAt;
        this.movieVersion = movieVersion;
        this.image = image;
        this.imageBkd = imageBkd;
        this.videoId = videoId;
        this.halls = new ArrayList<>();
    }
}
