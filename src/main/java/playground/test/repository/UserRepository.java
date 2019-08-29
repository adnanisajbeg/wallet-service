package playground.test.repository;

import org.springframework.data.repository.CrudRepository;
import playground.test.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
