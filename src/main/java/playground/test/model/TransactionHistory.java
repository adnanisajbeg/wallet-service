package playground.test.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(unique = true)
    private UUID uuid;
    @ManyToOne
    private Player player;
    @NotNull
    private Action action;
    private long amount;
    @NotNull
    private Status status;

    public TransactionHistory() {
    }

    public TransactionHistory(@NotNull UUID uuid, @NotNull Player player, @NotNull Action action, @Min(0) long amount) {
        this.uuid = uuid;
        this.player = player;
        this.action = action;
        this.amount = amount;
        this.status = Status.REQUESTED;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionHistory that = (TransactionHistory) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", player=" + player +
                ", action=" + action +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
