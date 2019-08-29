package playground.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import playground.test.model.User;
import playground.test.model.UserDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static playground.test.utils.UserUtils.createUserWithRandomUsername;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {
    @Autowired
    UserService userService;

    @Test
    public void saving_correct_user() {
        // Given
        UserDTO userDTO = createUserWithRandomUsername();

        // When
        User user = userService.addNewUser(userDTO);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getBalance()).isEqualTo(0);

        assertThat(user.getUsername())
                .isNotNull()
                .isNotBlank()
                .isEqualTo(userDTO.getUsername());
    }

    @Test
    public void when_adding_new_user_saved_object_has_an_id() {
        // Given
        UserDTO userDTO = createUserWithRandomUsername();

        // When
        User user = userService.addNewUser(userDTO);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isGreaterThan(0);
    }
}
