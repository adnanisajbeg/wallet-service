package playground.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.test.exceptions.PlayerNotFoundException;
import playground.test.model.CreditSubmitDTO;
import playground.test.model.Player;
import playground.test.service.PlayerService;
import playground.test.service.WalletService;

import java.util.UUID;

import static playground.test.utils.Messages.CREDIT_ADDED_SUCCESSFULLY_MESSAGE;
import static playground.test.utils.Messages.USERNAME_NOT_FOUND_ERROR_MESSAGE;

@RestController
public class WalletController {
    @Autowired
    PlayerService playerService;

    @Autowired
    WalletService walletService;

    @GetMapping("/wallet")
    public ResponseEntity<String> getBalance(@RequestParam String username) {
        Player player = playerService.findUserByUsername(username);

        if (player != null) {
            return new ResponseEntity<>(player.getBalance().toString(), HttpStatus.OK);
        }

        return new ResponseEntity<>(USERNAME_NOT_FOUND_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/wallet/credit")
    public ResponseEntity<String> addCredit(@RequestBody CreditSubmitDTO creditSubmitDTO) {
        try {
            walletService.addCreditForPlayer(creditSubmitDTO);
        } catch (PlayerNotFoundException pnfe) {
            return new ResponseEntity<>(USERNAME_NOT_FOUND_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CREDIT_ADDED_SUCCESSFULLY_MESSAGE + creditSubmitDTO.getUsername() + "!", HttpStatus.OK);
    }
}
