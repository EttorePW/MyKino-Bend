package CodersBay.Kino.movie;

import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.pk.Movie_plays_in;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private String title;
    private String mainCharacter;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate premieredAt;
    private MovieVersion movieVersion;
    private String image;
    private String imageBkd;
    private String videoId;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movie_plays_in> moviePlaysInList = new ArrayList<>();

    public Movie (String title, String mainCharacter, String description, LocalDate premieredAt, MovieVersion movieVersion, String image,String imageBkd, String videoId) {
        this.title = title;
        this.mainCharacter = mainCharacter;
        this.description = description;
        this.premieredAt = premieredAt;
        this.movieVersion = movieVersion;
        this.image = image;
        this.imageBkd = imageBkd;
        this.videoId = videoId;
        this.moviePlaysInList = new ArrayList<>();
    };

    @PostLoad
    private void initializeLists() {
        if (this.moviePlaysInList == null) {
            this.moviePlaysInList = new ArrayList<>();
        }
    }

    public List<Movie_plays_in> getMoviePlaysInList() {
        if (this.moviePlaysInList == null) {
            this.moviePlaysInList = new ArrayList<>();
        }
        return this.moviePlaysInList;
    }
}
