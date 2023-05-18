package com.elham.bankproject.model;

public class Transaction {
    private final String id;
    private final Long time;
    private final double amount;
    private final String accAId;
    private final String accBId;
    private final String type;

    public Transaction(String id, Long time, double amount, String accAId, String accBId, String type) {
        this.id = id;
        this.time = time;
        this.amount = amount;
        this.accAId = accAId;
        this.accBId = accBId;
        this.type = type;
    }

    public String getAccAId() {
        return accAId;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccBId() {
        return accBId;
    }

    public Long getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "," + time + "," + amount + "," + accAId + "," + accBId + "," + type;
    }
}
