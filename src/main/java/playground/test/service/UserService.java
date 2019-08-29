package playground.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.test.model.User;
import playground.test.model.UserDTO;
import playground.test.repository.UserRepository;

@Component
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User addNewUser(UserDTO userDTO) {
        User user = new User(userDTO);
        return userRepository.save(user);
    }
}
