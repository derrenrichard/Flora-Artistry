package model;

import java.util.List;

public class Transaction {
    private String transactionId;
    private String userId;
    private List<TransactionDetail> details;

    public Transaction(String transactionId, String userId) {
        this.transactionId = transactionId;
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public List<TransactionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<TransactionDetail> details) {
        this.details = details;
    }
}
