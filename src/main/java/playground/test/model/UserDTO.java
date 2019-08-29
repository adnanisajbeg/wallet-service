package playground.test.model;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {
    private String username;
    private Long balance;

    public String getUsername() {
        return username;
    }

    public Long getBalance() {
        return balance;
    }

    public UserDTO() {
    }

    public UserDTO(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(username, userDTO.username) &&
                Objects.equals(balance, userDTO.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, balance);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
