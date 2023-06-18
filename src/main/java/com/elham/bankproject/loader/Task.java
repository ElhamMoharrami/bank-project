package com.elham.bankproject.loader;

import com.elham.bankproject.common.CsvReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Task implements Callable<Long> {
    private final int trWriteId;
    private final String fileLoc;
    private List<Long> respectiveTimes = new ArrayList<>();

    public Task(int trWriteId, String fileLoc) {
        this.trWriteId = trWriteId;
        this.fileLoc = fileLoc;
    }

    public Long call() {
        long startTime = System.currentTimeMillis();
        CsvReader csvReader = new CsvReader();
        List<String> transactionList = csvReader.readFile(fileLoc + "/transaction" + trWriteId + ".csv");
        TableLoader tableLoaderT = TableLoader.loadToDb("transactions", transactionList);
        tableLoaderT.setSql("transactions");
        tableLoaderT.load();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
