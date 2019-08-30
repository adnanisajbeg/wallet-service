package playground.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.test.model.Player;
import playground.test.model.PlayerDTO;
import playground.test.repository.PlayerRepository;

@Component
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;

    public Player addNewPlayer(PlayerDTO playerDTO) {
        Player player = new Player(playerDTO);
        return playerRepository.save(player);
    }

    public Player findUserByUsername(String username) {
        return playerRepository.findByUsername(username);
    }
}
