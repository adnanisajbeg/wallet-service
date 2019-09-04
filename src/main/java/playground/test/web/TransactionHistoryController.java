package playground.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import playground.test.exceptions.InvalidInputException;
import playground.test.exceptions.PlayerNotFoundException;
import playground.test.model.TransactionHistoryDTO;
import playground.test.model.TransactionHistoryResponse;
import playground.test.service.TransactionHistoryService;

import java.util.List;

@RestController
public class TransactionHistoryController {
    @Autowired
    TransactionHistoryService transactionHistoryService;

    @GetMapping("/history")
    public ResponseEntity<TransactionHistoryResponse> getHistory(@RequestParam String username) {
        TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
        HttpStatus httpStatus = HttpStatus.OK;


        try {
            List<TransactionHistoryDTO> historyList = transactionHistoryService.getHistory(username);
            historyList.forEach(transactionHistoryResponse::addTransactionHistory);
        } catch (InvalidInputException iie) {
            transactionHistoryResponse.addError(iie.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(transactionHistoryResponse, httpStatus);
    }

}
