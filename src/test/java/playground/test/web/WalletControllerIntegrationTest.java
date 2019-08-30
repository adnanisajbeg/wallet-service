package playground.test.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import playground.test.model.PlayerDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.test.utils.PlayerUtils.createPlayerWithRandomUsername;

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
        PlayerDTO player = createPlayerWithRandomUsername();
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add", player, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        // When
        ResponseEntity<String> balance = restTemplate.getForEntity("http://localhost:" + randomServerPort + "/wallet?username={param1}", String.class, player.getUsername());

        // Then
        assertThat(balance).isNotNull();
        assertThat(balance.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balance.getBody()).isNotNull().isEqualTo("0");

    }
}
