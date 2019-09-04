package playground.test.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionHistoryResponse {
    private List<TransactionHistoryDTO> transactionHistoryList = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    public TransactionHistoryResponse addTransactionHistory(TransactionHistoryDTO transactionHistoryDTO) {
        transactionHistoryList.add(transactionHistoryDTO);
        return this;
    }

    public TransactionHistoryResponse addError(String error) {
        errors.add(error);
        return this;
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
        return Objects.equals(transactionHistoryList, that.transactionHistoryList) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionHistoryList, errors);
    }

    @Override
    public String toString() {
        return "TransactionHistoryResponse{" +
                "transactionHistoryList=" + transactionHistoryList +
                ", errors=" + errors +
                '}';
    }
}
