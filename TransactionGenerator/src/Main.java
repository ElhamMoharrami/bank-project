import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner getFileLoc = new Scanner(System.in);
        System.out.println("Where do you want to save the files?");
        String fileLoc = getFileLoc.nextLine();

        CustomerGenerator.generateCustomers(20);
        List<Customer> customerList = CustomerGenerator.getCustomers();

        AccountGenerator.generateAccount(customerList);
        List<Account> accountList = AccountGenerator.getAccounts();

        CsvWriter<Customer> customerWriter = new CsvWriter<>("customers.txt", fileLoc, customerList);
        customerWriter.writeToFile();

        CsvWriter<Account> accountWriter = new CsvWriter<>("accounts.txt", fileLoc, accountList);
        accountWriter.writeToFile();

        CsvWriter<Transaction> transactionWriter = new CsvWriter<>("transaction.txt", fileLoc, TransactionGenerator.generateTransaction(accountList));
        transactionWriter.writeToFile();
    }
}

