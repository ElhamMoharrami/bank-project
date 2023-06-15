package com.elham.bankproject.loader;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DataLoader {
    private static final Logger logger = LogManager.getLogger(DataLoader.class);

    public static void main(String[] args) {
        try {
            ConfigLoader loadConfig = new ConfigLoader();
            String fileLoc = loadConfig.loadConfig("files.destination");
            int maxNumberOfAccounts = Integer.parseInt(loadConfig.loadConfig("accountgenerator.bound.max"));
            int maxNumberOfTransactions = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.max"));
            int eachCsvFileTransactionLimit = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.limit"));
            int numberOfTransactionStatus = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.statusCount"));
            int numberOfCustomers = Integer.parseInt(loadConfig.loadConfig("customergenerator.customerCount"));
            int threadOrSequential = Integer.parseInt(loadConfig.loadConfig("loader.thread.count"));
            TableCreator tableCreatorC = TableCreator.addTablesToDb("customers");
            tableCreatorC.createTable();
            TableCreator tableCreatorA = TableCreator.addTablesToDb("accounts");
            tableCreatorA.createTable();
            TableCreator tableCreatorT = TableCreator.addTablesToDb("transactions");
            tableCreatorT.createTable();
            CsvReader csvReader = new CsvReader();
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
            if (threadOrSequential == 0) {
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
            } else if (threadOrSequential > 0) {
                int numberOfTransactions = numberOfCustomers * maxNumberOfTransactions * maxNumberOfAccounts * numberOfTransactionStatus;
                int numberOfTasks = (int) Math.ceil(numberOfTransactions / eachCsvFileTransactionLimit);
                System.out.println(numberOfTasks);
                ExecutorService executor = Executors.newFixedThreadPool(threadOrSequential);
                for (int i = 0; i < numberOfTasks; i++) {
                    Runnable task = new Task(i, fileLoc);
                    executor.execute(task);
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    System.err.println("Thread pool interrupted: " + e.getMessage());
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("please enter a valid path to config.properties");
        }
    }

    private static class Task implements Runnable {
        private final int trWriteId;
        private final String fileLoc;

        public Task(int trWriteId, String fileLoc) {
            this.trWriteId = trWriteId;
            this.fileLoc = fileLoc;
        }

        public void run() {
            CsvReader csvReader = new CsvReader();
            List<String> transactionList = csvReader.readFile(fileLoc + "/transaction" + trWriteId + ".csv");
            TableLoader tableLoaderT = TableLoader.loadToDb("transactions", transactionList);
            tableLoaderT.setSql("transactions");
            tableLoaderT.load();
        }
    }
}