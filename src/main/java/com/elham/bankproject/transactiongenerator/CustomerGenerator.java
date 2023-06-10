package com.elham.bankproject.transactiongenerator;

import com.elham.bankproject.model.Customer;

import java.util.*;

public class CustomerGenerator {
    private final int customerCount;

    public CustomerGenerator(int customerCount) {
        this.customerCount = customerCount;
    }

    public List<Customer> generateCustomers() {
        final List<String> fName = Arrays.asList("Julian", "Dante", "Jacks", "Scarlet", "Tella", "Nicolas");
        final List<String> lName = Arrays.asList("Santos", "Dragna", "Duarte", "Green", "Blake", "Roans");
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= customerCount; i++) {
            long customerId = Long.parseLong(String.valueOf(i));
            Random random = new Random();
            String name = fName.get(random.nextInt(fName.size())) + " " + lName.get(random.nextInt(lName.size()));
            long address = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
            customers.add(new Customer(customerId, name, String.valueOf(address)));
        }
        return customers;
    }
}
