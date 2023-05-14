package com.elham.bankproject.loader;

import com.elham.bankproject.common.ConnectDB;

public abstract class InsertToTable {
    private final ConnectDB createConnection= new ConnectDB();

    public ConnectDB getCreateConnection() {
        return createConnection;
    }

    public static void insertAllData(String filePath) {
        new InsertCustomers().insert(filePath);
        new InsertAccounts().insert(filePath);
        new InsertTransactions().insert(filePath);
    }

    abstract void insert(String files);
}
