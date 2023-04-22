import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String files = args[0];
        boolean quitFlag = false;

        while (!quitFlag) {
            System.out.println("Please Enter A Name or enter q to quit:" + "\n");
            Scanner input = new Scanner(System.in);
            String name = input.nextLine();
            if (name.equals("q")) {
                quitFlag = true;
            }
            boolean nameExists = false;

            List<String> customers = CsvReader.readFile(files + "/customers.csv");
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
                List<String> accounts = CsvReader.readFile(files + "/accounts.csv");
                for (int i = 0; i < accounts.size(); i++) {
                    String[] account = accounts.get(i).split(",");
                    for (int j = 0; j < ids.size(); j++) {
                        if (account[0].equals(ids.get(j))) {
                            accountList.add(account[1]);
                        }
                    }
                }
                List<String> transactions = CsvReader.readFile(files + "/transaction.csv");
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
}
}