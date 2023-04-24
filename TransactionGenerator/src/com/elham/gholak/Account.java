package com.elham.gholak;

public class Account {
    private final int accountId;
    private final String customerId;

    public Account(int accountId, String customerId) {
        this.accountId = accountId;
        this.customerId = customerId;
    }

    public int getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return
                customerId+ ","+accountId;
    }
}
