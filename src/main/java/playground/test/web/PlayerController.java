package playground.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import playground.test.model.PlayerDTO;
import playground.test.service.PlayerService;

@RestController
public class PlayerController {
    @Autowired
    PlayerService playerService;

    @PostMapping("/player/add")
    public boolean addPlayer(@RequestBody PlayerDTO newPlayer) {
        playerService.addNewPlayer(newPlayer);
        return true;
    }
}
