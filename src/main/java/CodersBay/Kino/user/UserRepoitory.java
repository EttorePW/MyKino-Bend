package CodersBay.Kino.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoitory extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);
}
