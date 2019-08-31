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

import static playground.test.utils.Messages.USERNAME_TAKEN_ERROR_MESSAGE;
import static playground.test.utils.Messages.USER_CREATED_MESSAGE;

@RestController
public class PlayerController {
    @Autowired
    PlayerService playerService;

    @PostMapping("/player/add")
    public ResponseEntity<String> addPlayer(@RequestBody String username) {
        try {
            playerService.addNewPlayer(new PlayerDTO(username));
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(USERNAME_TAKEN_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(USER_CREATED_MESSAGE, HttpStatus.OK);
    }
}
