package CodersBay.Kino.hall;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends MongoRepository<Hall, String> {
    List<Hall> findByHallIdIn(List<String> halls);
    List<Hall> findByCinemaId(String cinemaId);
}
