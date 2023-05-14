package com.elham.bankproject.searcher;

public class CustomerTransaction {
    private final String customer_name;
    private final String customerId;
    private final String transactionTime;
    private final String amount;
    private final String srcAcc;
    private final String destAcc;
    private final String transactionType;

    public CustomerTransaction(String customer_name, String customerId, String transactionTime, String amount,
                               String srcAcc, String destAcc, String transactionType) {
        this.customer_name = customer_name;
        this.customerId = customerId;
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.srcAcc = srcAcc;
        this.destAcc = destAcc;
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return customer_name + "," + customerId + "," + transactionTime + "," + amount + ", " + srcAcc + "," +
                destAcc + "," + transactionType;
    }
}
