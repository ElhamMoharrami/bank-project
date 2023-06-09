package com.elham.bankproject.searcher;

public class CustomerTransaction {
    private final String customerName;
    private final Long customerId;
    private final Long transactionTime;
    private final String amount;
    private final Long srcAcc;
    private final Long destAcc;
    private final String transactionType;

    public CustomerTransaction(String customerName, Long customerId, Long transactionTime, String amount,
                               Long srcAcc, Long destAcc, String transactionType) {
        this.customerName = customerName.toLowerCase();
        this.customerId = customerId;
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.srcAcc = srcAcc;
        this.destAcc = destAcc;
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return customerName + "," + customerId + "," + transactionTime + "," + amount + ", " + srcAcc + "," +
                destAcc + "," + transactionType;
    }
}
