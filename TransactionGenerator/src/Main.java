import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args)  {
        CustomerGenerator.generateCustomers(20);
        List<Customer> customerList = CustomerGenerator.getCustomers();

        AccountGenerator.generateAccount(customerList);
        List<Account> accountList = AccountGenerator.getAccounts();

        CsvWriter<Customer> customerWriter = new CsvWriter<>("customers.txt", customerList);
        customerWriter.writeToFile();

        CsvWriter<Account> accountWriter = new CsvWriter<>("accounts.txt", accountList);
        accountWriter.writeToFile();

        TransactionGenerator.generateTransaction(accountList);
    }
}
