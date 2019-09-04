package playground.test.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionHistoryResponse {
    private PlayerDTO playerDTO;
    private List<TransactionHistoryDTO> transactionHistoryList = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    public TransactionHistoryResponse setPlayer(PlayerDTO playerDTO) {
        this.playerDTO = playerDTO;
        return this;
    }

    public TransactionHistoryResponse addTransactionHistory(TransactionHistoryDTO transactionHistoryDTO) {
        transactionHistoryList.add(transactionHistoryDTO);
        return this;
    }

    public TransactionHistoryResponse addError(String error) {
        errors.add(error);
        return this;
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }

    public List<TransactionHistoryDTO> getTransactionHistoryList() {
        return transactionHistoryList;
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionHistoryResponse that = (TransactionHistoryResponse) o;
        return Objects.equals(playerDTO, that.playerDTO) &&
                Objects.equals(transactionHistoryList, that.transactionHistoryList) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerDTO, transactionHistoryList, errors);
    }

    @Override
    public String toString() {
        return "TransactionHistoryResponse{" +
                "playerDTO=" + playerDTO +
                ", transactionHistoryList=" + transactionHistoryList +
                ", errors=" + errors +
                '}';
    }
}
