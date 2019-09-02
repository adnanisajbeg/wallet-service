package playground.test.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(unique = true)
    private String username;
    @Min(0)
    private Long balance;

    public Player() {
    }

    public Player(PlayerDTO playerDTO) {
        username = playerDTO.getUsername();
        balance = 0L;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id &&
                Objects.equals(username, player.username) &&
                Objects.equals(balance, player.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, balance);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }

    public void addCredit(Long credit) {
        this.balance += credit;
    }

    public void withdraw(Long credit) {
        this.balance -= credit;
    }
}
