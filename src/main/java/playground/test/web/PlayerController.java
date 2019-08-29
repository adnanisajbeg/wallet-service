package playground.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addPlayer(@RequestBody PlayerDTO newPlayer) {
        try {
            playerService.addNewPlayer(newPlayer);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>("Username already taken!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User created!", HttpStatus.OK);
    }
}
