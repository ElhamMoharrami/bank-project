import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountGenerator {
    private static final List<String> accIds = new ArrayList<>();
    private static final List<Account> accounts = new ArrayList<>();

    public static void generateAccount(List<Customer> customersList) {
        Random random = new Random();
        for (Customer customer : customersList) {
            int countAccounts = random.nextInt(3) + 1;
            for (int j = 1; j <= countAccounts; j++) {
                String accountId = String.valueOf(j);
                accIds.add(accountId);
                Account account = new Account(accountId, customer.getCustomerId());
                accounts.add(account);
            }
        }
    }

    public static List<String> getAccIds() {
        return accIds;
    }

    public static List<Account> getAccounts() {
        return accounts;
    }
}
