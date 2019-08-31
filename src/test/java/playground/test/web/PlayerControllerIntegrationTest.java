package playground.test.web;

import org.apache.commons.lang3.RandomStringUtils;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.test.utils.Messages.USERNAME_TAKEN_ERROR_MESSAGE;
import static playground.test.utils.Messages.USER_CREATED_MESSAGE;
import static playground.test.utils.PlayerUtils.createRequestEntityWithRandomUsername;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void when_adding_new_player_true_is_returned() {
        // Given
        HttpEntity<String> playerWithRandomUsername = createRequestEntityWithRandomUsername();

        // When
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add", playerWithRandomUsername, String.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull().isEqualTo(USER_CREATED_MESSAGE);
    }

    @Test
    public void when_adding_new_player_with_existing_username_error_is_returned() {
        // Given
        HttpEntity<String> playerWithRandomUsername = createRequestEntityWithRandomUsername();

        // When
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add", playerWithRandomUsername, String.class);
        ResponseEntity<String> result2 = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/player/add", playerWithRandomUsername, String.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull().isEqualTo(USER_CREATED_MESSAGE);

        assertThat(result2).isNotNull();
        assertThat(result2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result2.getBody()).isNotNull().isEqualTo(USERNAME_TAKEN_ERROR_MESSAGE);
    }
}
