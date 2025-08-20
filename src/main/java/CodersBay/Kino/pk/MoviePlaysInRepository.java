package CodersBay.Kino.pk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MoviePlaysInRepository extends JpaRepository<Movie_plays_in, Movie_plays_in_PK> {
    void deleteMovie_plays_inByMovie_MovieId(Long id);
    
    @Query("SELECT mpi FROM Movie_plays_in mpi WHERE mpi.movie.movieId = :movieId")
    List<Movie_plays_in> findByMovieMovieId(@Param("movieId") Long movieId);
    
    @Query(value = "SELECT hall_id FROM movie_plays_in WHERE movie_id = :movieId", nativeQuery = true)
    List<Long> findHallIdsByMovieId(@Param("movieId") Long movieId);
}
