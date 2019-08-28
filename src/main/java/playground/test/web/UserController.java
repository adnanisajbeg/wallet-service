package playground.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import playground.test.model.UserDTO;
import playground.test.service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user/add")
    public boolean addUser(@RequestBody UserDTO newUser) {
        userService.addNewUser(newUser);
        return true;
    }
}
