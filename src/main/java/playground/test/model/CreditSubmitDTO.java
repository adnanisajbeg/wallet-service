package playground.test.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CreditSubmitDTO implements Serializable {
    private UUID id;
    private String username;
    private Long credit;

    public CreditSubmitDTO(UUID id, String username, Long credit) {
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
        CreditSubmitDTO that = (CreditSubmitDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CreditSubmitDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", credit=" + credit +
                '}';
    }
}
