package playground.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;
import playground.test.exceptions.InsufficientFundsException;
import playground.test.exceptions.PlayerNotFoundException;
import playground.test.model.Action;
import playground.test.model.CreditSubmitDTO;
import playground.test.model.DebitSubmitDTO;
import playground.test.model.Player;
import playground.test.validator.PlayerValidator;

import static playground.test.utils.Messages.PLAYER_NOT_FOUND_MESSAGE;

@Component
public class WalletService {
    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerValidator playerValidator;

    @Autowired
    TransactionHistoryService transactionHistoryService;

    public void addCreditForPlayer(CreditSubmitDTO creditSubmitDTO) {
        playerValidator.validateCreditSubmitDTO(creditSubmitDTO);
        Player player = findPlayer(creditSubmitDTO.getUsername());
        transactionHistoryService.addHistory(creditSubmitDTO.getId(), player, Action.CREDIT, creditSubmitDTO.getCredit());
        player.addCredit(creditSubmitDTO.getCredit());
        playerService.savePlayer(player);
    }

    public void withdrawForPlayer(DebitSubmitDTO debitSubmitDTO) {
        Player player = findPlayer(debitSubmitDTO.getUsername());
        transactionHistoryService.addHistory(debitSubmitDTO.getId(), player, Action.DEBIT, debitSubmitDTO.getCredit());
        try {
            player.withdraw(debitSubmitDTO.getCredit());
            playerService.savePlayer(player);
        } catch (TransactionSystemException tse) {
            throw new InsufficientFundsException();
        }
//        transactionHistoryService.success(debitSubmitDTO.getId());
    }

    private Player findPlayer(String username) {
        Player player = playerService.findUserByUsername(username);

        if (player == null) {
            throw new PlayerNotFoundException(PLAYER_NOT_FOUND_MESSAGE);
        }

        return player;
    }
}
