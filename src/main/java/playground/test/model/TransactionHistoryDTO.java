package playground.test.model;

import java.util.Objects;
import java.util.UUID;

public class TransactionHistoryDTO {
    private UUID uuid;
    private Action action;
    private long amount;
    private Status status;

    public TransactionHistoryDTO(UUID uuid, Action action, long amount, Status status) {
        this.uuid = uuid;
        this.action = action;
        this.amount = amount;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
        TransactionHistoryDTO that = (TransactionHistoryDTO) o;
        return amount == that.amount &&
                Objects.equals(uuid, that.uuid) &&
                action == that.action &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, action, amount, status);
    }

    @Override
    public String toString() {
        return "TransactionHistoryDTO{" +
                "uuid=" + uuid +
                ", action=" + action +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
