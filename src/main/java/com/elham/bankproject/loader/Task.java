package com.elham.bankproject.loader;

import com.elham.bankproject.common.CsvReader;

import java.util.List;

public class Task implements Runnable {
    private final int trWriteId;
    private final String fileLoc;

    public Task(int trWriteId, String fileLoc) {
        this.trWriteId = trWriteId;
        this.fileLoc = fileLoc;
    }

    public void run() {
        CsvReader csvReader = new CsvReader();
        List<String> transactionList = csvReader.readFile(fileLoc + "/transaction" + trWriteId + ".csv");
        TableLoader tableLoaderT = TableLoader.loadToDb("transactions", transactionList);
        tableLoaderT.setSql("transactions");
        tableLoaderT.load();
    }
}
