package CodersBay.Kino.pk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePlaysInRepository extends JpaRepository<Movie_plays_in, Movie_plays_in_PK> {
    void deleteMovie_plays_inByMovie_MovieId(Long id);
}
