package playground.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.test.exceptions.InvalidInputException;
import playground.test.exceptions.PlayerNotFoundException;
import playground.test.model.*;
import playground.test.repository.PlayerRepository;
import playground.test.repository.TransactionHistoryRepository;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static playground.test.utils.Messages.USERNAME_NOT_FOUND_ERROR_MESSAGE;

@Component
public class TransactionHistoryService {
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    PlayerService playerService;

    public void addHistory(UUID uuid, Player player, Action action, long amount) {
        TransactionHistory transactionHistory = new TransactionHistory(uuid, player, action, amount);
        transactionHistoryRepository.save(transactionHistory);
    }

    public void success(UUID uuid) {
        statusChanged(uuid, Status.SUCCESS);
    }

    public void failed(UUID uuid) {
        statusChanged(uuid, Status.FAILED);
    }

    public List<TransactionHistoryDTO> getHistory(String username) {
        Player player = playerService.findUserByUsername(username);

        if (player == null) {
            throw new InvalidInputException(USERNAME_NOT_FOUND_ERROR_MESSAGE);
        }

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findByPlayer(player);

        return createResultList(transactionHistoryList);
    }

    private List<TransactionHistoryDTO> createResultList(List<TransactionHistory> transactionHistoryList) {
        List<TransactionHistoryDTO> result = new ArrayList<>();

        if (transactionHistoryList != null && !transactionHistoryList.isEmpty()) {
            for (TransactionHistory transactionHistory : transactionHistoryList) {
                result.add(new TransactionHistoryDTO(
                        transactionHistory.getUuid(),
                        transactionHistory.getAction(),
                        transactionHistory.getAmount(),
                        transactionHistory.getStatus()));
            }
        }

        return result;
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
