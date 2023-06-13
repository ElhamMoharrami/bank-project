package com.elham.bankproject.loader;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

public class DataLoader {
    private static final Logger logger = LogManager.getLogger(DataLoader.class);

    public static void main(String[] args) {
        try {
            ConfigLoader loadConfig = new ConfigLoader();
            String fileLoc = loadConfig.loadConfig("files.destination");
            int threadOrSequential = Integer.parseInt(loadConfig.loadConfig("loader.thread.count"));
            TableCreator tableCreatorC = TableCreator.addTablesToDb("customers");
            tableCreatorC.createTable();
            TableCreator tableCreatorA = TableCreator.addTablesToDb("accounts");
            tableCreatorA.createTable();
            TableCreator tableCreatorT = TableCreator.addTablesToDb("transactions");
            tableCreatorT.createTable();
            CsvReader csvReader = new CsvReader();
            if (threadOrSequential == 0) {
                long startCustomerLoadTimeMillis = System.currentTimeMillis();
                List<String> customersList = csvReader.readFile(fileLoc + "/customers.csv");
                TableLoader tableLoaderC = TableLoader.loadToDb("customers", customersList);
                tableLoaderC.setSql("customers");
                tableLoaderC.load();
                long endCustomerLoadTimeMillis = System.currentTimeMillis();
                long timeToLoadCustomers = endCustomerLoadTimeMillis - startCustomerLoadTimeMillis;
                logger.info("customers loaded successfully. took " + timeToLoadCustomers + " milli seconds.");
                long startAccountLoadTimeMillis = System.currentTimeMillis();
                List<String> accountsList = csvReader.readFile(fileLoc + "/accounts.csv");
                TableLoader tableLoaderA = TableLoader.loadToDb("accounts", accountsList);
                tableLoaderA.setSql("accounts");
                tableLoaderA.load();
                long endLoadLoadTimeMillis = System.currentTimeMillis();
                long timeToLoadAccounts = endLoadLoadTimeMillis - startAccountLoadTimeMillis;
                logger.info("accounts loaded successfully. took " + timeToLoadAccounts + " milli seconds.");
                int count = 0;
                boolean flag = true;
                while (flag) {
                    List<String> transactionList = csvReader.readFile(fileLoc + "/transaction" + count + ".csv");
                    if (transactionList.size() > 1) {
                        long startTransactionLoadTimeMillis = System.currentTimeMillis();
                        TableLoader tableLoaderT = TableLoader.loadToDb("transactions", transactionList);
                        tableLoaderT.setSql("transactions");
                        tableLoaderT.load();
                        long endTransactionLoadTimeMillis = System.currentTimeMillis();
                        long timeToLoadTransactions = endTransactionLoadTimeMillis - startTransactionLoadTimeMillis;
                        logger.info("transaction file " + count + " loaded successfully. took " + timeToLoadTransactions +
                                " milli seconds");
                        count++;
                    } else {
                        flag = false;
                    }
                }
            } else if (threadOrSequential == 1) {
                long startCustomerLoadTimeMillis = System.currentTimeMillis();
                List<String> customersList = csvReader.readFile(fileLoc + "/customers.csv");
                TableLoader tableLoaderC = TableLoader.loadToDb("customers", customersList);
                tableLoaderC.setSql("customers");
                Thread loadC = new Thread(() -> tableLoaderC.load());
                loadC.start();
                long endCustomerLoadTimeMillis = System.currentTimeMillis();
                long timeToLoadCustomers = endCustomerLoadTimeMillis - startCustomerLoadTimeMillis;
                logger.info("customers loaded successfully. took " + timeToLoadCustomers + " milli seconds.");
                long startAccountLoadTimeMillis = System.currentTimeMillis();
                List<String> accountsList = csvReader.readFile(fileLoc + "/accounts.csv");
                TableLoader tableLoaderA = TableLoader.loadToDb("accounts", accountsList);
                tableLoaderA.setSql("accounts");
                Thread loadA = new Thread(() -> tableLoaderA.load());
                loadA.start();
                long endLoadLoadTimeMillis = System.currentTimeMillis();
                long timeToLoadAccounts = endLoadLoadTimeMillis - startAccountLoadTimeMillis;
                logger.info("accounts loaded successfully. took " + timeToLoadAccounts + " milli seconds.");
                int count = 0;
                boolean flag = true;
                while (flag) {
                    List<String> transactionList = csvReader.readFile(fileLoc + "/transaction" + count + ".csv");
                    if (transactionList.size() > 1) {
                        long startTransactionLoadTimeMillis = System.currentTimeMillis();
                        TableLoader tableLoaderT = TableLoader.loadToDb("transactions", transactionList);
                        tableLoaderT.setSql("transactions");
                        Thread loadT = new Thread(() -> tableLoaderT.load());
                        loadT.start();
                        long endTransactionLoadTimeMillis = System.currentTimeMillis();
                        long timeToLoadTransactions = endTransactionLoadTimeMillis - startTransactionLoadTimeMillis;
                        logger.info("transaction file " + count + " loaded successfully. took " + timeToLoadTransactions +
                                " milli seconds");
                        count++;
                    } else {
                        flag = false;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("please enter a valid path to config.properties");
        }
    }
}