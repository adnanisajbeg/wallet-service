package playground.test.repository;

import org.springframework.data.repository.CrudRepository;
import playground.test.model.Player;
import playground.test.model.TransactionHistory;

import java.util.List;
import java.util.UUID;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer> {
    TransactionHistory findByUuid(UUID uuid);
    List<TransactionHistory> findByPlayer(Player player);
}
