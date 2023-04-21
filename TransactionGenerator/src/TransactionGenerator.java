import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TransactionGenerator {

    public static List<Transaction> generateTransaction(List<Account> accounts) {
        List<Transaction> transactionList = new ArrayList<>();
        Random random = new Random();
        List<Integer> accountIds = AccountGenerator.getAccIds();
        int count_transactions = random.nextInt(20 + 100) + 100;
        for (Account account : accounts) {
            for (int i = 0; i < count_transactions; i++) {
                long startEpochDay = LocalDate.of(2005, Month.JANUARY, 1).toEpochDay();
                long endEpochDay = LocalDate.now().toEpochDay();
                long date = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
//                    LocalDate date = LocalDate.ofEpochDay(randomDay);
                double amount = Double.parseDouble(getRandomValue());
                int acc_b = accountIds.get((int) (Math.random() * accountIds.size()));
                String type = TransactType.randomType().toString();
                if (acc_b == account.getAccountId()) {
                    Transaction transactionF = new Transaction(date, amount, account.getAccountId(), acc_b, "Failed");
                    transactionList.add(transactionF);
                } else {
                    Transaction transactionA = new Transaction(date, amount, account.getAccountId(), acc_b, type);
                    transactionList.add(transactionA);
                    Transaction transactionB = new Transaction(date, amount, acc_b, account.getAccountId(), type.equals("CREDIT") ? "DEBIT" : "CREDIT");
                    transactionList.add(transactionB);
                }
            }
        }
        return transactionList;
    }

    public static String getRandomValue() {
        final Random random = new Random();
        final double dbl = random.nextDouble() * (1000 - 1) + 1;
        return String.format("%." + 2 + "f", dbl);
    }
}
