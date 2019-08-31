package playground.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import playground.test.model.Player;
import playground.test.service.PlayerService;

import static playground.test.utils.Messages.USERNAME_NOT_FOUND_ERROR_MESSAGE;

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

        return new ResponseEntity<>(USERNAME_NOT_FOUND_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
