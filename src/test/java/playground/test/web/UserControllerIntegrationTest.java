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
import playground.test.model.UserDTO;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void when_adding_new_user_true_is_returned() {
        // Given
        HttpEntity<UserDTO> randomNewUser = createUserWithRandomUsername();

        // When
        ResponseEntity<Boolean> result = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/user/add", randomNewUser, Boolean.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull().isTrue();
    }

    private HttpEntity<UserDTO> createUserWithRandomUsername() {
        String username = RandomStringUtils.random(10);
        return new HttpEntity<>(new UserDTO(username));
    }
}
