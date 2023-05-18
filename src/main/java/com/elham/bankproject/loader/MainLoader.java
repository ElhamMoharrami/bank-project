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
            loadConfig.setConfigLoc(args[0]);
            String fileLoc = loadConfig.loadConfig("files.destination");
            TableCreator createTable = new TableCreator();
            createTable.createTable();

            CsvReader csvReader = new CsvReader();
            TableInsertor tableInsertor = new TableInsertor();

            List<String> customersList = csvReader.readFile(fileLoc + "/customers.csv");
            String[] customerColumns = {"customer_id", "customer_name", "post_address"};
            tableInsertor.insert(customersList, "customers", customerColumns);

            List<String> accountsList = csvReader.readFile(fileLoc + "/accounts.csv");
            String[] accountcolumns = {"customer_id", "account_id"};
            tableInsertor.insert(accountsList, "accounts", accountcolumns);

            List<String> transactionList = csvReader.readFile(fileLoc + "/transaction.csv");
            String[] transactionscolumns = {"transaction_id", "transaction_time", "amount", "src_acc ", "dest_acc", "transaction_type"};
            tableInsertor.insert(transactionList, "transactions", transactionscolumns);

            long endTimeMillis = System.currentTimeMillis();
            long timeToLoad = endTimeMillis - startTimeMillis;
            logger.info("loading files to database took " + timeToLoad);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("please enter a valid path to config.properties");
        }
    }
}