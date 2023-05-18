package com.elham.bankproject.transactiongenerator;

import java.util.Random;

enum TransactType {
    CREDIT, DEBIT;
    private static final Random rand = new Random();
    public static TransactType randomType() {
        TransactType[] transactionType = values();
        return transactionType[rand.nextInt(transactionType.length)];
    }
}