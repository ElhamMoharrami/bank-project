import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            String fileLoc = args[0];
            ConfigLoader loadConfig = new ConfigLoader();
            loadConfig.setConfigLoc(args[1]);
            loadConfig.loadConfig("customergenerator.customerCount");
            String customerCount = loadConfig.getProperty();
            CustomerGenerator.generateCustomers(Integer.parseInt(customerCount));
            List<Customer> customerList = CustomerGenerator.getCustomers();
            AccountGenerator.generateAccount(customerList);
            List<Account> accountList = AccountGenerator.getAccounts();
            CsvWriter<Customer> customerWriter = new CsvWriter<>("customers.csv", fileLoc, customerList);
            customerWriter.writeToFile("customerId,name,postAddress" + "\n");
            CsvWriter<Account> accountWriter = new CsvWriter<>("accounts.csv", fileLoc, accountList);
            accountWriter.writeToFile("customerId,accountId" + "\n");
            CsvWriter<Transaction> transactionWriter = new CsvWriter<>("transaction.csv", fileLoc, TransactionGenerator.generateTransaction(accountList));
            transactionWriter.writeToFile("epochTime,amount,sourceAcc,destinationAcc,type" + "\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No directory Entered. Please Enter a Directory and the path to config file.");
        } catch (NumberFormatException e) {
            System.out.println("couldn't get value from config file");
        }
    }
}


