import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter files directory path: ");
        Scanner getFiles = new Scanner(System.in);
        String files = getFiles.nextLine();

        System.out.println("Please Enter A Name:" + "\n");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        boolean nameExists = false;

        List<String> customers = CsvReader.readFile(files + "/customers.txt");
        List<String> ids = new ArrayList<>();
        List<String> accountList = new ArrayList<>();

        for (int i = 0; i < customers.size(); i++) {
            String[] user = customers.get(i).split(",");
            if (user[1].equals(name)) {
                ids.add(user[0]);
                nameExists = true;
            }
        }
        if (nameExists) {
            List<String> accounts = CsvReader.readFile(files + "/accounts.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] account = accounts.get(i).split(",");
                for (int j = 0; j < ids.size(); j++) {
                    if (account[0].equals(ids.get(j))) {
                        accountList.add(account[1]);
                    }
                }
            }
            List<String> transactions = CsvReader.readFile(files + "/transaction.txt");
            for (int i = 0; i < transactions.size(); i++) {
                String[] transaction = transactions.get(i).split(",");
                for (int j = 0; j < accountList.size(); j++) {
                    if (transaction[2].equals(accountList.get(j))) {
                        System.out.println("account " + accountList.get(j) + " of " + name + " has transaction with amount  " + transaction[1] + " sent to " + transaction[3]);
                    }
                }
            }
        } else {
            System.out.println("Entered name does not exist or is invalid");
        }
    }
}