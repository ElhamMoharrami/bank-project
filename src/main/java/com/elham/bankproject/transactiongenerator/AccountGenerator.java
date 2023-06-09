package com.elham.bankproject.transactiongenerator;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountGenerator {
    private static final List<Integer> accIds = new ArrayList<>();
    private static final List<Account> accounts = new ArrayList<>();
    private final List<Customer> customersList;

    public AccountGenerator(List<Customer> customersList) {
        this.customersList = customersList;
    }

    public List<Account> generateAccount() {
        ConfigLoader loadConfig = new ConfigLoader();
        int accountMinBound = Integer.parseInt(loadConfig.loadConfig("accountgenerator.bound.min"));
        int accountMaxBound = Integer.parseInt(loadConfig.loadConfig("accountgenerator.bound.max"));
        Random random = new Random();
        int accountId = 1;
        for (Customer customer : customersList) {
            int countAccounts = random.nextInt(accountMaxBound) + accountMinBound;
            for (int j = 1; j <= countAccounts; j++) {
                accIds.add(accountId);
                Account account = new Account(customer.getCustomerId(),accountId);
                accounts.add(account);
                accountId += 1;
            }
        }
        return accounts;
    }

    public static List<Integer> getAccIds() {
        return accIds;
    }
}
