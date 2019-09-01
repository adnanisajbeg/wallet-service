package playground.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.test.exceptions.PlayerNotFoundException;
import playground.test.model.CreditSubmitDTO;
import playground.test.model.Player;

import java.util.UUID;

import static playground.test.utils.Messages.PLAYER_NOT_FOUND_MESSAGE;

@Component
public class WalletService {
    @Autowired
    PlayerService playerService;

    public void addCreditForPlayer(CreditSubmitDTO creditSubmitDTO) {
        Player player = findPlayer(creditSubmitDTO.getUsername());
        player.addCredit(creditSubmitDTO.getCredit());
        playerService.savePlayer(player);
    }

    private Player findPlayer(String username) {
        Player player = playerService.findUserByUsername(username);

        if (player == null) {
            throw new PlayerNotFoundException(PLAYER_NOT_FOUND_MESSAGE);
        }

        return player;
    }
}
