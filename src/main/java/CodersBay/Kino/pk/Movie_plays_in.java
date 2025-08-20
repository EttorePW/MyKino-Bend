package CodersBay.Kino.pk;

import CodersBay.Kino.hall.Hall;
import CodersBay.Kino.movie.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie_plays_in")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(Movie_plays_in_PK.class)
public class Movie_plays_in {

    @Id
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
