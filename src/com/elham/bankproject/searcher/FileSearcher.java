package com.elham.bankproject.searcher;

import com.elham.bankproject.common.CsvReader;
import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.model.Transaction;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSearcher implements Searcher {
    private final String filePath;
    public String customerName;
    private List<String> existingCustomersIds;
    public Map<String, ArrayList<String>> existingCustomersData;
    private List<CustomerTransaction> result;

    public FileSearcher(String filePath) {
        this.filePath = filePath;
    }

    public List<String> existingCustomers(List<String> customers) {
        this.existingCustomersIds = new ArrayList<>();
        for (String s : customers) {
            String[] customerData = s.split(",");
            Customer customer = new Customer(customerData[0], customerData[1], customerData[2]);
            if (customer.getName().equals(customerName)) {
                existingCustomersIds.add(customer.getCustomerId());
            }
        }
        return existingCustomersIds;
    }

    public void customerAccountData(List<String> accounts) {
        this.existingCustomersData = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            String[] accountData = accounts.get(i).split(",");
            Account account = new Account(accountData[0], accountData[1]);
            ArrayList<String> customerAccounts = new ArrayList<>();
            for (int j = 0; j < existingCustomersIds.size(); j++) {
                if (account.getCustomerId().equals(existingCustomersIds.get(j))) {
                    customerAccounts.add(account.getAccountId());
                }
            }
            existingCustomersData.put(account.getCustomerId(), customerAccounts);
        }
    }

    public void createTransactionList(List<String> transactions) {
        for (int i = 0; i < transactions.size(); i++) {
            String[] transactionData = transactions.get(i).split(",");
            Transaction transaction = new Transaction(transactionData[0], Long.parseLong(transactionData[1]),
                    Double.parseDouble(transactionData[2]), transactionData[3], transactionData[4], transactionData[5]);
            for (Map.Entry<String, ArrayList<String>> entry : existingCustomersData.entrySet()) {
                ArrayList<String> values = entry.getValue();
                for (int j = 0; j < values.size(); j++) {
                    if (values.get(j).equals(transaction.getAccAId())) {
                        CustomerTransaction customerT = new CustomerTransaction(customerName, entry.getKey(),
                                Long.toString(transaction.getTime()), Double.toString(transaction.getAmount()),
                                transaction.getAccAId(), transaction.getAccBId(), transaction.getType());
                        result.add(customerT);
                    }
                }
            }
        }
    }

    @Override
    public List<CustomerTransaction> search(String keyword) {
        try {
            this.customerName = keyword;
            CsvReader reader = new CsvReader();
            this.result = new ArrayList<>();
            List<String> customers = reader.readFile(filePath + "/customers.csv");
            if (reader.isValidDirectory()) {
                List<String> existingCustomerIds = this.existingCustomers(customers);
                if (existingCustomerIds.size() > 0) {
                    List<String> accounts = reader.readFile(filePath + "/accounts.csv");
                    customerAccountData(accounts);
                    List<String> transactions = reader.readFile(filePath + "/transaction.csv");
                    createTransactionList(transactions);
                } else {
                    System.out.println("customer does not exist.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
