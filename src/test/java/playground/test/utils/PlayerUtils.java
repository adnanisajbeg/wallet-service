package playground.test.utils;

import org.apache.commons.lang3.RandomStringUtils;
import playground.test.model.PlayerDTO;

public class PlayerUtils {
    private PlayerUtils() {}

    public static PlayerDTO createPlayerWithRandomUsername() {
        String username = RandomStringUtils.random(10);
        return new PlayerDTO(username);
    }
}
