package CodersBay.Kino.movie;

import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.pk.Movie_plays_in;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "main_character")
    private String mainCharacter;
    
    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "premiered_at")
    private LocalDate premieredAt;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "movie_version")
    private MovieVersion movieVersion;
    
    @Column(name = "image")
    private String image;
    
    @Column(name = "image_bkd")
    private String imageBkd;
    
    @Column(name = "video_id")
    private String videoId;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
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

    };
}
