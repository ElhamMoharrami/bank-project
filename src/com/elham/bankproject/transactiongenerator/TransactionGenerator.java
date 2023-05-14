package com.elham.bankproject.transactiongenerator;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Transaction;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionGenerator {
    public List<Account> accounts;
    public int transactionMinBound;
    public int transactionMaxBound;

    public TransactionGenerator(List<Account> accounts, int transactionMinBound, int transactionMaxBound) {
        this.accounts = accounts;
        this.transactionMinBound = transactionMinBound;
        this.transactionMaxBound = transactionMaxBound;
    }

    public List<Transaction> generateTransaction() {
        List<Transaction> transactionList = new ArrayList<>();
        Random random = new Random();
        List<Integer> accountIds = AccountGenerator.getAccIds();
        int count_transactions = random.nextInt(transactionMinBound + transactionMaxBound) + transactionMaxBound;
        for (Account account : accounts) {
            for (int i = 0; i < count_transactions; i++) {
                String transactionId = String.valueOf(i);
                long now = System.currentTimeMillis();
                long timeSinceTransaction = random.nextInt(14 * 24 * 60 * 60 * 1000);
                long date = now - timeSinceTransaction;
                double amount = Double.parseDouble(getRandomValue());
                String accB = Integer.toString(accountIds.get((int) (Math.random() * accountIds.size())));
                String type = TransactType.randomType().toString();
                if (accB.equals(account.getAccountId())) {
                    Transaction transactionF = new Transaction(transactionId, date, amount, account.getAccountId(), accB, "Failed");
                    transactionList.add(transactionF);
                } else {
                    Transaction transactionA = new Transaction(transactionId, date, amount, account.getAccountId(), accB, type);
                    transactionList.add(transactionA);
                    Transaction transactionB = new Transaction(transactionId, date, amount, accB, account.getAccountId(), type.equals("CREDIT") ? "DEBIT" : "CREDIT");
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
