package com.elham.bankproject.model;

public class Customer {
    private final long customerId;
    private final String name;
    private final String address;

    public Customer(long customerId, String name, String address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return customerId + "," + name + "," + address;
    }
}