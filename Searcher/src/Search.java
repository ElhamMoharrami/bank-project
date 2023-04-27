import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Search {
    public static void search(String files) {
        boolean quitFlag = false;
        CsvReader reader = new CsvReader();
        List<String> customers = reader.readFile(files + "/customers.csv");
        List<String> ids = new ArrayList<>();
        List<String> accountList = new ArrayList<>();

        while (!quitFlag && reader.isValidDirectory()) {
            System.out.println("Please Enter A Name or enter q to quit:" + "\n");
            Scanner input = new Scanner(System.in);
            String name = input.nextLine();
            if (name.equals("q")) {
                quitFlag = true;
            }
            boolean nameExists = false;

            for (int i = 0; i < customers.size(); i++) {
                Customer customer = new Customer(customers.get(i).split(","));
                if (customer.getCustomerName().equals(name)) {
                    ids.add(customer.getCustomerId());
                    nameExists = true;
                }
            }
            if (nameExists) {
                List<String> accounts = reader.readFile(files + "/accounts.csv");
                for (int i = 0; i < accounts.size(); i++) {
                    Account account = new Account(accounts.get(i).split(","));
                    for (int j = 0; j < ids.size(); j++) {
                        if (account.getCustomerId().equals(ids.get(j))) {
                            accountList.add(account.getAccountId());
                        }
                    }
                }
                List<String> transactions = reader.readFile(files + "/transaction.csv");
                for (int i = 0; i < transactions.size(); i++) {
                    Transaction transaction = new Transaction(transactions.get(i).split(","));
                    for (int j = 0; j < accountList.size(); j++) {
                        if (transaction.getSrcAcc().equals(accountList.get(j))) {
                            System.out.println("account " + accountList.get(j) + " of " + name + " has transaction with amount  " + transaction.getAmount() + " sent to " + transaction.getDestAcc());
                        }
                    }
                }
            } else if (!name.equals("q")) {
                System.out.println("Entered name does not exist or is invalid");
            }
        }
    }
}
