package CodersBay.Kino.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoitory extends MongoRepository<User, String> {
    User findByUserEmail(String userEmail);
}
