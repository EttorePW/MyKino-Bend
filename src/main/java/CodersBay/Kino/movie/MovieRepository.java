package CodersBay.Kino.movie;

import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.pk.Movie_plays_in;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> getMoviesByMovieVersion(MovieVersion movieVersion);

    Movie findByTitle(String title);
    
    @Query("SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.moviePlaysInList mpl LEFT JOIN FETCH mpl.hall h")
    List<Movie> findAllWithHalls();
}
