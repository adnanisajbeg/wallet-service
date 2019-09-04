package playground.test.web;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionHistoryController {

    @GetMapping("/history")
    public HttpEntity<String> getHistory(@RequestParam String username) {
        return null;
    }

}
