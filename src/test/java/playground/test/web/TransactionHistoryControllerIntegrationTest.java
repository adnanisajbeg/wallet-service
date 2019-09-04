package playground.test.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import playground.test.model.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.test.utils.Messages.USERNAME_NOT_FOUND_ERROR_MESSAGE;
import static playground.test.utils.PlayerUtils.createRequestEntityWithRandomUsername;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionHistoryControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void when_requesting_transaction_history_for_existing_player_his_history_is_returned() {
        // Given
        HttpEntity<String> player = createRequestEntityWithRandomUsername();
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add",
                player, String.class);
        UUID creditId1 = UUID.randomUUID();
        UUID creditId2 = UUID.randomUUID();
        UUID debitId1 = UUID.randomUUID();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(creditId1, player.getBody(), 10L), String.class);
         restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(creditId2, player.getBody(), 121L), String.class);
        restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/debit",
                new DebitSubmitDTO(debitId1, player.getBody(), 12L), String.class);

        // When
        ResponseEntity<TransactionHistoryResponse> transactionHistory =
                restTemplate.getForEntity("http://localhost:" + randomServerPort + "/history?username={param1}",
                TransactionHistoryResponse.class, player.getBody());

        // Then
        assertThat(transactionHistory).isNotNull();
        assertThat(transactionHistory.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transactionHistory.getBody()).isNotNull();
        assertThat(transactionHistory.getBody().hasErrors()).isNotNull();
        assertThat(transactionHistory.getBody().getTransactionHistoryList())
                .isNotNull()
                .hasSize(3)
                .contains(new TransactionHistoryDTO(creditId1, Action.CREDIT, 10L, Status.SUCCESS),
                        new TransactionHistoryDTO(creditId2, Action.CREDIT, 121L, Status.SUCCESS));
                        new TransactionHistoryDTO(debitId1, Action.DEBIT, 12L, Status.SUCCESS);

    }

    @Test
    public void when_one_failed_debit_history_will_contain_status_failed() {
        // Given
        HttpEntity<String> player = createRequestEntityWithRandomUsername();
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add",
                player, String.class);
        UUID creditId1 = UUID.randomUUID();
        UUID creditId2 = UUID.randomUUID();
        UUID debitId1 = UUID.randomUUID();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(creditId1, player.getBody(), 10L), String.class);
        restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/debit",
                new DebitSubmitDTO(debitId1, player.getBody(), 12L), String.class);
        restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(creditId2, player.getBody(), 121L), String.class);

        // When
        ResponseEntity<TransactionHistoryResponse> transactionHistory =
                restTemplate.getForEntity("http://localhost:" + randomServerPort + "/history?username={param1}",
                        TransactionHistoryResponse.class, player.getBody());

        // Then
        assertThat(transactionHistory).isNotNull();
        assertThat(transactionHistory.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transactionHistory.getBody()).isNotNull();
        assertThat(transactionHistory.getBody().hasErrors()).isNotNull();
        assertThat(transactionHistory.getBody().getTransactionHistoryList())
                .isNotNull()
                .hasSize(3)
                .contains(new TransactionHistoryDTO(creditId1, Action.CREDIT, 10L, Status.SUCCESS),
                        new TransactionHistoryDTO(creditId2, Action.CREDIT, 121L, Status.SUCCESS));
        new TransactionHistoryDTO(debitId1, Action.DEBIT, 12L, Status.FAILED);
    }

    @Test
    public void when_asked_history_for_non_existing_player_error_is_return() {
        // Given
        HttpEntity<String> player = createRequestEntityWithRandomUsername();

        // When
        ResponseEntity<TransactionHistoryResponse> transactionHistory =
                restTemplate.getForEntity("http://localhost:" + randomServerPort + "/history?username={param1}",
                        TransactionHistoryResponse.class, player.getBody());

        // Then
        assertThat(transactionHistory).isNotNull();
        assertThat(transactionHistory.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(transactionHistory.getBody()).isNotNull();
        assertThat(transactionHistory.getBody().hasErrors()).isTrue();
        assertThat(transactionHistory.getBody().getTransactionHistoryList())
                .isNotNull()
                .isEmpty();
        assertThat(transactionHistory.getBody().getErrors())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(USERNAME_NOT_FOUND_ERROR_MESSAGE);
    }
}
