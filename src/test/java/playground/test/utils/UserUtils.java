package playground.test.utils;

import org.apache.commons.lang3.RandomStringUtils;
import playground.test.model.UserDTO;

public class UserUtils {
    private UserUtils() {}

    public static UserDTO createUserWithRandomUsername() {
        String username = RandomStringUtils.random(10);
        return new UserDTO(username);
    }
}
