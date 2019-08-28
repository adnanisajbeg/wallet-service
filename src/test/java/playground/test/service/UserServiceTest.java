package playground.test.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import playground.test.model.User;
import playground.test.model.UserDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setup() {
        userService = new UserService();
    }

    @Test
    public void when_adding_new_user_true_is_returned() {
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

    private UserDTO createUserWithRandomUsername() {
        String username = RandomStringUtils.random(10);
        return new UserDTO(username);
    }
}
