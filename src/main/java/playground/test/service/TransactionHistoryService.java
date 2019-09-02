package playground.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.test.model.Action;
import playground.test.model.Player;
import playground.test.model.Status;
import playground.test.model.TransactionHistory;
import playground.test.repository.TransactionHistoryRepository;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Component
public class TransactionHistoryService {
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    public void addHistory(UUID uuid, Player player, Action action, long amount) {
        TransactionHistory transactionHistory = new TransactionHistory(uuid, player, action, amount);
        System.out.println(transactionHistory);
        transactionHistoryRepository.save(transactionHistory);
    }

    public void success(UUID uuid) {
        statusChanged(uuid, Status.SUCCESS);
    }

    public void failed(UUID uuid) {
        statusChanged(uuid, Status.FAILED);
    }

    void statusChanged(UUID uuid, @NotNull  Status status) {
        TransactionHistory transactionHistory = transactionHistoryRepository.findByUuid(uuid);
        if (transactionHistory == null) {
            // TODO: ERROR logging
        } else {
            transactionHistory.setStatus(status);
            transactionHistoryRepository.save(transactionHistory);
        }
    }
}
