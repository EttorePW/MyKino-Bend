package CodersBay.Kino.hall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall, Long> {
    List<Hall> findByHallIdIn(List<Long> halls);
}
