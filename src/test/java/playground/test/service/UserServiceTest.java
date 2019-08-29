package playground.test.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import playground.test.model.User;
import playground.test.model.UserDTO;
import playground.test.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static playground.test.utils.UserUtils.createUserWithRandomUsername;


public class UserServiceTest {
    private UserService userService;
    UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setup() {
        userService = new UserService();
        userService.userRepository = userRepository;
    }

    @Test
    public void when_adding_new_user_saved_object_contains_given_username() {
        // Given
        UserDTO userDTO = createUserWithRandomUsername();
        when(userRepository.save(new User(userDTO))).thenReturn(new User(userDTO));

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


}
