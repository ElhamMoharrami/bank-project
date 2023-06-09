package com.elham.bankproject.searcher;

import com.elham.bankproject.common.CsvReader;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSearcher implements Searcher {
    private final String filePath;
    public String customerName;
    private List<Long> existingCustomersIds;
    public Map<Long, ArrayList<Long>> existingCustomersData;
    private List<CustomerTransaction> result;
    private static final Logger logger = LogManager.getLogger(FileSearcher.class);

    public FileSearcher(String filePath) {
        this.filePath = filePath;
    }

    public List<Long> existingCustomers(List<String> customers) {
        this.existingCustomersIds = new ArrayList<>();
        for (String s : customers) {
            String[] customerData = s.split(",");
            Customer customer = new Customer(Long.parseLong(customerData[0]), customerData[1], customerData[2]);
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
            Account account = new Account(Long.parseLong(accountData[0]), Long.parseLong(accountData[1]));
            ArrayList<Long> customerAccounts = new ArrayList<>();
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
            Transaction transaction = new Transaction(Long.parseLong(transactionData[0]), Long.parseLong(transactionData[1]),
                    Double.parseDouble(transactionData[2]), Long.parseLong(transactionData[3]), Long.parseLong(transactionData[4]), transactionData[5]);
            for (Map.Entry<Long, ArrayList<Long>> entry : existingCustomersData.entrySet()) {
                ArrayList<Long> values = entry.getValue();
                for (int j = 0; j < values.size(); j++) {
                    if (values.get(j).equals(transaction.getAccAId())) {
                        CustomerTransaction customerT = new CustomerTransaction(customerName, entry.getKey(),
                                transaction.getTime(), Double.toString(transaction.getAmount()),
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
            System.out.println(keyword);
            CsvReader reader = new CsvReader();
            this.result = new ArrayList<>();
            List<String> customers = reader.readFile(filePath + "/customers.csv");
            System.out.println(filePath + "/customers.csv");
            if (reader.isValidDirectory()) {
                List<Long> existingCustomerIds = this.existingCustomers(customers);
                if (existingCustomerIds.size() > 0) {
                    List<String> accounts = reader.readFile(filePath + "/accounts.csv");
                    customerAccountData(accounts);

                    int counter = 0;
                    boolean quitFlag = true;
                    while (quitFlag) {
                        List<String> transactions = reader.readFile(filePath + "/transaction" + counter + ".csv");
                        counter++;
                        if (transactions.size() > 1) {
                            createTransactionList(transactions);
                        } else {
                            quitFlag = false;
                        }
                    }
                } else {
                    logger.info("customer does not exist.");
                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }
}
