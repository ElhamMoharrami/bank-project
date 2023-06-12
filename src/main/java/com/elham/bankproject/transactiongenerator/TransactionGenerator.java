package com.elham.bankproject.transactiongenerator;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionGenerator {
    private final List<Account> accounts;
    public int transactionMinBound;
    public int transactionMaxBound;
    private final ConfigLoader loadConfig = new ConfigLoader();

    private static final Logger logger = LogManager.getLogger(TransactionGenerator.class);

    public TransactionGenerator(List<Account> accounts, int transactionMinBound, int transactionMaxBound) {
        this.accounts = accounts;
        this.transactionMinBound = transactionMinBound;
        this.transactionMaxBound = transactionMaxBound;
    }

    public void generateTransaction() {
        List<Transaction> transactionList = new ArrayList<>();
        Random random = new Random();
        List<Integer> accountIds = AccountGenerator.getAccIds();
        int countTransactions = random.nextInt(transactionMinBound + transactionMaxBound) + transactionMaxBound;
        long transactionId = 0;
        int count = 0;
        int transactionWriterLimit = Integer.parseInt(loadConfig.loadConfig
                ("transactiongenerator.transaction.limit"));
        for (Account account : accounts) {
            for (int i = 0; i < countTransactions; i++) {
                long now = System.currentTimeMillis();
                long timeSinceTransaction = random.nextInt(14 * 24 * 60 * 60 * 1000);
                long date = now - timeSinceTransaction;
                double amount = Double.parseDouble(getRandomValue());
                long accB = accountIds.get((int) (Math.random() * accountIds.size()));
                String type = TransactType.randomType().toString();
                if (accB == account.getAccountId()) {
                    Transaction transactionF = new Transaction(transactionId += 1, date, amount, account.getAccountId(),
                            accB, "Failed");
                    transactionList.add(transactionF);
                } else {
                    Transaction transactionA = new Transaction(transactionId += 2, date, amount, account.getAccountId(),
                            accB, type);
                    transactionList.add(transactionA);
                    Transaction transactionB = new Transaction(transactionId += 3, date, amount, accB,
                            account.getAccountId(), type.equals("CREDIT") ? "DEBIT" : "CREDIT");
                    transactionList.add(transactionB);
                }
                if (transactionList.size() >= transactionWriterLimit) {
                    this.writeTransactionToFile(transactionList, count);
                    transactionList.clear();
                    count++;
                }
            }
        }
    }

    public void writeTransactionToFile(List<Transaction> transactionList, int count) {
        String fileLoc = loadConfig.loadConfig("files.destination");
        long startTransactionGenerateTimeMillis = System.currentTimeMillis();
        CsvWriter<Transaction> transactionWriter = new CsvWriter<>("transaction" + count + ".csv",
                fileLoc);
        transactionWriter.writeToFile("TransactionId,EpochTime,Amount,SourceAcc,DestinationAcc,Type",
                transactionList);
        long endTransactionGenerateTimeMillis = System.currentTimeMillis();
        long timeToGenerateTransaction = endTransactionGenerateTimeMillis - startTransactionGenerateTimeMillis;
        logger.info("transaction file number " + count + " generated in " + fileLoc + " . took " +
                timeToGenerateTransaction + " milli seconds.");
    }

    public static String getRandomValue() {
        final Random random = new Random();
        final double dbl = random.nextDouble() * (1000 - 1) + 1;
        return String.format("%." + 2 + "f", dbl);
    }
}
