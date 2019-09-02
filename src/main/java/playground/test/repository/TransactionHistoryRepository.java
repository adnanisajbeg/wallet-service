package playground.test.repository;

import org.springframework.data.repository.CrudRepository;
import playground.test.model.TransactionHistory;

import java.util.UUID;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer> {
    TransactionHistory findByUuid(UUID uuid);
}
