package com.elham.bankproject.transactiongenerator;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MainTransactionGenerator {
    private static final Logger logger = LogManager.getLogger(MainTransactionGenerator.class);

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
            customerWriter.writeToFile("customerId,name,postAddress", customerList);
            CsvWriter<Account> accountWriter = new CsvWriter<>("accounts.csv", fileLoc);
            accountWriter.writeToFile("customerId,accountId", accountList);
            CsvWriter<Transaction> transactionWriter = new CsvWriter<>("transaction.csv", fileLoc);
            int transactionMinBound = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.min"));
            int transactionMaxBound = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.max"));
            TransactionGenerator transactionGenerator = new TransactionGenerator(accountList, transactionMinBound, transactionMaxBound);
            transactionWriter.writeToFile("transactionId,epochTime,amount,sourceAcc,destinationAcc,type", transactionGenerator.generateTransaction());
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("No directory Entered. Please Enter a Directory and the path to config file.");
        } catch (NumberFormatException e) {
            logger.error("couldn't get value from config file");
        }
    }
}


