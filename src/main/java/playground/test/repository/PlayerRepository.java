package playground.test.repository;

import org.springframework.data.repository.CrudRepository;
import playground.test.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Player findByUsername(String username);
}
