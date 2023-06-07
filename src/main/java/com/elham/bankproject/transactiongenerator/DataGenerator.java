package com.elham.bankproject.transactiongenerator;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DataGenerator {
    private static final Logger logger = LogManager.getLogger(DataGenerator.class);

    public static void main(String[] args) {
        try {
            ConfigLoader loadConfig = new ConfigLoader();
            int customerCount = Integer.parseInt(loadConfig.loadConfig("customergenerator.customerCount"));
            CustomerGenerator customerGenerator = new CustomerGenerator(customerCount);
            List<Customer> customerList = customerGenerator.generateCustomers();
            AccountGenerator accountGenerator = new AccountGenerator(customerList);
            List<Account> accountList = accountGenerator.generateAccount();
            String fileLoc = loadConfig.loadConfig("files.destination");
            CsvWriter<Customer> customerWriter = new CsvWriter<>("customers.csv", fileLoc);
            customerWriter.writeToFile("CustomerId,Name,PostAddress", customerList);
            CsvWriter<Account> accountWriter = new CsvWriter<>("accounts.csv", fileLoc);
            accountWriter.writeToFile("CustomerId,AccountId", accountList);
            int transactionMinBound = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.min"));
            int transactionMaxBound = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.max"));
            TransactionGenerator transactionGenerator = new TransactionGenerator(accountList, transactionMinBound, transactionMaxBound);
            List<List<Transaction>> transactionsList = transactionGenerator.generateTransaction();
            int count=0;
            for (List<Transaction> transactionList : transactionsList) {
                CsvWriter<Transaction> transactionWriter = new CsvWriter<>("transaction"+count+".csv", fileLoc);
                transactionWriter.writeToFile("TransactionId,EpochTime,Amount,SourceAcc,DestinationAcc,Type",transactionList );
                count++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("No directory Entered. Please Enter a Directory and the path to config file.");
        } catch (NumberFormatException e) {
            logger.error("couldn't get value from config file");
        }
    }
}


