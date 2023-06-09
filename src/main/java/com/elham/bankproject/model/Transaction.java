package com.elham.bankproject.model;

public class Transaction {
    private final Long id;
    private final Long time;
    private final double amount;
    private final Long accAId;
    private final Long accBId;
    private final String type;

    public Transaction(Long id, Long time, double amount, Long accAId, Long accBId, String type) {
        this.id = id;
        this.time = time;
        this.amount = amount;
        this.accAId = accAId;
        this.accBId = accBId;
        this.type = type;
    }

    public Long getAccAId() {
        return accAId;
    }

    public double getAmount() {
        return amount;
    }

    public Long getAccBId() {
        return accBId;
    }

    public Long getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "," + time + "," + amount + "," + accAId + "," + accBId + "," + type;
    }
}
