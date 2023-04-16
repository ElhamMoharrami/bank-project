import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TransactionGenerator {
    public static void generateTransaction(List<Account> accounts) {
        try (BufferedWriter transactionsFile = new BufferedWriter(new FileWriter("transactions.txt"))) {
            Random random = new Random();
            List<String> accountIds = AccountGenerator.getAccIds();
            int count_transactions = random.nextInt(20 + 100) + 100;
            for (Account account : accounts) {
                for (int i = 0; i < count_transactions; i++) {
                    long startEpochDay = LocalDate.of(2005, Month.JANUARY, 1).toEpochDay();
                    long endEpochDay = LocalDate.now().toEpochDay();
                    long date = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
//                    LocalDate date = LocalDate.ofEpochDay(randomDay);
                    double amount = Double.parseDouble(getRandomValue());
                    String acc_b = accountIds.get((int) (Math.random() * accountIds.size()));
                    String type = TransactType.randomType().toString();
                    if (acc_b.equals(account.getAccountId())) {
                        Transaction transactionF = new Transaction(date, amount, account.getAccountId(), acc_b, "Failed");
                        transactionsFile.write(transactionF.toString());
                    } else {
                        Transaction transactionA = new Transaction(date, amount, account.getAccountId(), acc_b, type);
                        transactionsFile.write(transactionA.toString());
                        Transaction transactionB = new Transaction(date, amount, acc_b, account.getAccountId(), type.equals("CREDIT") ? "DEBIT" : "CREDIT");
                        transactionsFile.write(transactionB.toString());
                    }
                }
            }
            System.out.println("transactions.txt generated successfully.");
        } catch (IOException e) {
            System.out.println("Something went wrong.Could not generate transactions.txt.");
        }
    }

    public static String getRandomValue() {
        final Random random = new Random();
        final double dbl = random.nextDouble() * (1000 - 1) + 1;
        return String.format("%." + 2 + "f", dbl);
    }
}
