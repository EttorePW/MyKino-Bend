package CodersBay.Kino.movie;

import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.pk.Movie_plays_in;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> getMoviesByMovieVersion(MovieVersion movieVersion);

    Movie findByTitle(String title);
}