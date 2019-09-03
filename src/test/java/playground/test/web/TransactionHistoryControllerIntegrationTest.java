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
import playground.test.model.CreditSubmitDTO;
import playground.test.model.DebitSubmitDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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
        ResponseEntity<String> transactionHistory = restTemplate.getForEntity("http://localhost:" + randomServerPort + "/history?username={param1}",
                String.class, player.getBody());

        // Then
    }
}
