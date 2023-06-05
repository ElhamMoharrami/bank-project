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
            long startTimeMillis = System.currentTimeMillis();
            ConfigLoader loadConfig = new ConfigLoader();
            String fileLoc = loadConfig.loadConfig("files.destination");
            CustomerTableCreator createCustomerTable = new CustomerTableCreator();
            createCustomerTable.createTable();
            AccountsTableCreator createAccountTable = new AccountsTableCreator();
            createAccountTable.createTable();
            TransactionTableCreator createTransactionTable = new TransactionTableCreator();
            createTransactionTable.createTable();
            CsvReader csvReader = new CsvReader();
            List<String> customersList = csvReader.readFile(fileLoc + "/customers.csv");
            CustomerLoader loadCustomers = new CustomerLoader();
            loadCustomers.load(customersList);
            List<String> accountsList = csvReader.readFile(fileLoc + "/accounts.csv");
            AccountLoader loadAccounts = new AccountLoader();
            loadAccounts.load(accountsList);
            List<String> transactionList = csvReader.readFile(fileLoc + "/transaction.csv");
            TransactionLoader loadTransactions = new TransactionLoader();
            loadTransactions.load(transactionList);
            long endTimeMillis = System.currentTimeMillis();
            long timeToLoad = endTimeMillis - startTimeMillis;
            logger.info("loading files to database took " + timeToLoad);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("please enter a valid path to config.properties");
        }
    }
}