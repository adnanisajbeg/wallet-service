package playground.test.service;

import org.springframework.stereotype.Component;
import playground.test.model.User;
import playground.test.model.UserDTO;

@Component
public class UserService {
    public User addNewUser(UserDTO userDTO) {
        // TODO: validate user

        User user = new User(userDTO);

        // TODO: save user

        return user;
    }
}
