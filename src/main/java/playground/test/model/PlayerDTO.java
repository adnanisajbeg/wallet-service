package playground.test.model;

import java.io.Serializable;
import java.util.Objects;

public class PlayerDTO implements Serializable {
    private String username;
    private Long balance;

    public String getUsername() {
        return username;
    }

    public Long getBalance() {
        return balance;
    }

    public PlayerDTO() {
    }

    public PlayerDTO(Player player) {
        this.username = player.getUsername();
        this.balance = player.getBalance();
    }

    public PlayerDTO(String username) {
        this.username = username;
        this.balance = 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO playerDTO = (PlayerDTO) o;
        return Objects.equals(username, playerDTO.username) &&
                Objects.equals(balance, playerDTO.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, balance);
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
