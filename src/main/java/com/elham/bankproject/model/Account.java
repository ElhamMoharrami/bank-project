package com.elham.bankproject.model;

public class Account {
    private final long accountId;
    private final long customerId;

    public Account(long customerId, long accountId) {
        this.accountId = accountId;
        this.customerId = customerId;
    }

    public  Long getAccountId() {
        return accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return customerId + "," + accountId;
    }
}
