package com.elham.bankproject.loader;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

public class MainLoader {
    private static final Logger logger = LogManager.getLogger(MainLoader.class);

    public static void main(String[] args) {
        try {
            ConfigLoader loadConfig = new ConfigLoader();
            String fileLoc = loadConfig.loadConfig("files.destination");
            CustomerTableCreator createCustomerTable = new CustomerTableCreator();
            createCustomerTable.createTable();
            AccountsTableCreator createAccountTable = new AccountsTableCreator();
            createAccountTable.createTable();
            TransactionTableCreator createTransactionTable = new TransactionTableCreator();
            createTransactionTable.createTable();
            CsvReader csvReader = new CsvReader();
            long startCustomerLoadTimeMillis = System.currentTimeMillis();
            List<String> customersList = csvReader.readFile(fileLoc + "/customers.csv");
            CustomerLoader loadCustomers = new CustomerLoader();
            loadCustomers.load(customersList);
            long endCustomerLoadTimeMillis = System.currentTimeMillis();
            long timeToLoadCustomers = endCustomerLoadTimeMillis - startCustomerLoadTimeMillis;
            logger.info("customers loaded successfully. took "+timeToLoadCustomers+" milli seconds.");
            long startAccountLoadTimeMillis = System.currentTimeMillis();
            List<String> accountsList = csvReader.readFile(fileLoc + "/accounts.csv");
            AccountLoader loadAccounts = new AccountLoader();
            loadAccounts.load(accountsList);
            long endLoadLoadTimeMillis = System.currentTimeMillis();
            long timeToLoadAccounts = endLoadLoadTimeMillis  - startAccountLoadTimeMillis;
            logger.info("accounts loaded successfully. took "+timeToLoadAccounts+" milli seconds.");
            int count = 0;
            boolean flag = true;
            while (flag) {
                List<String> transactionList = csvReader.readFile(fileLoc + "/transaction" + count + ".csv");
                if (transactionList.size() >1) {
                    long startTransactionLoadTimeMillis = System.currentTimeMillis();
                    TransactionLoader loadTransactions = new TransactionLoader();
                    loadTransactions.load(transactionList);
                    long endTransactionLoadTimeMillis = System.currentTimeMillis();
                    long timeToLoadTransactions = endTransactionLoadTimeMillis - startTransactionLoadTimeMillis;
                    logger.info("transaction file "+count+" loaded successfully. took "+timeToLoadTransactions+" milli seconds");
                    count++;
                } else {
                    flag = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("please enter a valid path to config.properties");
        }
    }
}