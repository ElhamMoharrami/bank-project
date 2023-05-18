package com.elham.bankproject.model;

public class Account {
    private final String accountId;
    private final String customerId;

    public Account(String customerId, String  accountId) {
        this.accountId = accountId;
        this.customerId = customerId;
    }

    public  String getAccountId() {
        return accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return customerId + "," + accountId;
    }
}
