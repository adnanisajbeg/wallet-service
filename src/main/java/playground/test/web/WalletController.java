package playground.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import playground.test.exceptions.InsufficientFundsException;
import playground.test.exceptions.InvalidInputException;
import playground.test.exceptions.PlayerNotFoundException;
import playground.test.model.CreditSubmitDTO;
import playground.test.model.DebitSubmitDTO;
import playground.test.model.Player;
import playground.test.service.PlayerService;
import playground.test.service.WalletService;

import static playground.test.utils.Messages.*;

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
        } catch (InvalidInputException iie) {
            return new ResponseEntity<>(iie.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CREDIT_ADDED_SUCCESSFULLY_MESSAGE + creditSubmitDTO.getUsername() + "!", HttpStatus.OK);
    }

    @PostMapping("/wallet/debit")
    public ResponseEntity<String> debit(@RequestBody DebitSubmitDTO debitSubmitDTO) {
        try {
            walletService.withdrawForPlayer(debitSubmitDTO);
        } catch (PlayerNotFoundException pnfe) {
            return new ResponseEntity<>(USERNAME_NOT_FOUND_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        } catch (InvalidInputException iie) {
            return new ResponseEntity<>(iie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InsufficientFundsException tse) {
            return new ResponseEntity<>(INSUFFICIENT_FUNDS_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(TRANSACTION_FAILED_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CREDIT_ADDED_SUCCESSFULLY_MESSAGE + debitSubmitDTO.getUsername() + "!", HttpStatus.OK);
    }
}
