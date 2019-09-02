package playground.test.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DebitSubmitDTO implements Serializable {
    private UUID id;
    private String username;
    private Long credit;

    public DebitSubmitDTO(UUID id, String username, Long credit) {
        this.id = id;
        this.username = username;
        this.credit = credit;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getCredit() {
        return credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DebitSubmitDTO that = (DebitSubmitDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DebitSubmitDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", credit=" + credit +
                '}';
    }
}
