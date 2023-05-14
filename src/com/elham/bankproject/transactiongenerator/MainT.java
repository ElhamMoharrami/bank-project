package com.elham.bankproject.transactiongenerator;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.model.Transaction;
import com.elham.bankproject.searcher.FileSearcher;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.List;

public class MainT {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger log = Logger.getLogger(FileSearcher.class.getName());
        try {
            ConfigLoader loadConfig = new ConfigLoader();
            loadConfig.setConfigLoc(args[0]);
            int customerCount = Integer.parseInt(loadConfig.loadConfig("customergenerator.customerCount"));
            CustomerGenerator customerGenerator = new CustomerGenerator(customerCount);
            List<Customer> customerList = customerGenerator.generateCustomers();
            AccountGenerator accountGenerator = new AccountGenerator(customerList);
            List<Account> accountList = accountGenerator.generateAccount();
            String fileLoc = loadConfig.loadConfig("files.destination");
            CsvWriter<Customer> customerWriter = new CsvWriter<>("customers.csv", fileLoc);
            customerWriter.writeToFile("customerId,name,postAddress" + "\n", customerList);
            CsvWriter<Account> accountWriter = new CsvWriter<>("accounts.csv", fileLoc);
            accountWriter.writeToFile("customerId,accountId" + "\n", accountList);
            CsvWriter<Transaction> transactionWriter = new CsvWriter<>("transaction.csv", fileLoc);
            int transactionMinBound = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.min"));
            int transactionMaxBound = Integer.parseInt(loadConfig.loadConfig("transactiongenerator.transaction.max"));
            TransactionGenerator transactionGenerator=new TransactionGenerator(accountList,transactionMinBound,transactionMaxBound);
            transactionWriter.writeToFile("transactionId,epochTime,amount,sourceAcc,destinationAcc,type" + "\n",transactionGenerator.generateTransaction());
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("No directory Entered. Please Enter a Directory and the path to config file.");
        } catch (NumberFormatException e) {
            log.error("couldn't get value from config file");
        }
    }
}


