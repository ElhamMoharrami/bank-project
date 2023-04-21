import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountGenerator {
    private static final List<Integer> accIds = new ArrayList<>();
    private static final List<Account> accounts = new ArrayList<>();

    public static void generateAccount(List<Customer> customersList) {
        Random random = new Random();
        int accountId = 1;
        for (Customer customer : customersList) {
            int countAccounts = random.nextInt(3) + 1;
            for (int j = 1; j <= countAccounts; j++) {
                accIds.add(accountId);
                Account account = new Account(accountId, customer.getCustomerId());
                accounts.add(account);
                accountId += 1;
            }
        }
    }

    public static List<Integer> getAccIds() {
        return accIds;
    }

    public static List<Account> getAccounts() {
        return accounts;
    }
}
