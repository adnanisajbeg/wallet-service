package playground.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import playground.test.model.Player;
import playground.test.model.PlayerDTO;
import playground.test.service.PlayerService;

@RestController
public class WalletController {
    @Autowired
    PlayerService playerService;

    @GetMapping("/wallet")
    public ResponseEntity<String> getBalance(@RequestParam String username) {
        Player player = playerService.findUserByUsername(username);

        if (player != null) {
            return new ResponseEntity<>(player.getBalance().toString(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid username!", HttpStatus.BAD_REQUEST);
    }
}
