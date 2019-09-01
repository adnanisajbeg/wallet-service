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
import playground.test.model.PlayerDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.test.utils.Messages.CREDIT_ADDED_SUCCESSFULLY_MESSAGE;
import static playground.test.utils.Messages.USERNAME_NOT_FOUND_ERROR_MESSAGE;
import static playground.test.utils.PlayerUtils.createPlayerWithRandomUsername;
import static playground.test.utils.PlayerUtils.createRequestEntityWithRandomUsername;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void when_creating_new_user_his_balance_is_0() {
        // Given
        HttpEntity<String> player = createRequestEntityWithRandomUsername();
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add",
                player, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        // When
        ResponseEntity<String> balance = restTemplate.getForEntity("http://localhost:" + randomServerPort + "/wallet?username={param1}",
                String.class, player.getBody());

        // Then
        assertThat(balance).isNotNull();
        assertThat(balance.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balance.getBody()).isNotNull().isEqualTo("0");
    }

    @Test
    public void when_asking_for_balance_for_non_existing_username_bad_request_is_returned() {
        // Given
        HttpEntity<String> player = createRequestEntityWithRandomUsername();

        // When
        ResponseEntity<String> balance = restTemplate.getForEntity("http://localhost:" + randomServerPort + "/wallet?username={param1}",
                String.class, player.getBody());

        // Then
        assertThat(balance).isNotNull();
        assertThat(balance.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(balance.getBody()).isNotNull().isEqualTo(USERNAME_NOT_FOUND_ERROR_MESSAGE);
    }

    @Test
    public void when_adding_balance_to_existing_player_correct_amount_is_added() {
        // Given
        HttpEntity<String> player = createRequestEntityWithRandomUsername();
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add",
                player, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID id = UUID.randomUUID();

        // When
        ResponseEntity<String> resultAddingBalance = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(id, player.getBody(), 10L), String.class);
        ResponseEntity<String> balance = restTemplate.getForEntity("http://localhost:" + randomServerPort + "/wallet?username={param1}",
                String.class, player.getBody());

        // Then
        assertThat(resultAddingBalance).isNotNull();
        assertThat(resultAddingBalance.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultAddingBalance.getBody()).isEqualTo(CREDIT_ADDED_SUCCESSFULLY_MESSAGE + player.getBody() + "!");
        assertThat(balance).isNotNull();
        assertThat(balance.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balance.getBody()).isEqualTo("10");
    }

    @Test
    public void when_adding_balance_to_non_existing_player_bad_request_is_returned() {
        // Given
        PlayerDTO player = createPlayerWithRandomUsername();
        UUID id = UUID.randomUUID();

        // When
        ResponseEntity<String> resultAddingBalance = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(id, player.getUsername(), 10L), String.class);

        // Then
        assertThat(resultAddingBalance).isNotNull();
        assertThat(resultAddingBalance.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(resultAddingBalance.getBody()).isEqualTo(USERNAME_NOT_FOUND_ERROR_MESSAGE);
    }

    @Test
    public void when_adding_multiple_credit_to_balance_to_existing_player_correct_amount_is_return() {
        // Given
        HttpEntity<String> player = createRequestEntityWithRandomUsername();
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add",
                player, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID id = UUID.randomUUID();

        // When
        restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(id, player.getBody(), 10L), String.class);
       restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(id, player.getBody(), 14L), String.class);
         restTemplate.postForEntity("http://localhost:" + randomServerPort + "/wallet/credit",
                new CreditSubmitDTO(id, player.getBody(), 22L), String.class);
        ResponseEntity<String> balance = restTemplate.getForEntity("http://localhost:" + randomServerPort + "/wallet?username={param1}",
                String.class, player.getBody());

        // Then
        assertThat(balance).isNotNull();
        assertThat(balance.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balance.getBody()).isEqualTo("46");
    }

}
