package com.elham.bankproject.searcher;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;

import java.util.List;
import java.util.Scanner;

public class CliSearcher {
    private  Searcher searcher;
    private final String filePath;
    private final static String HEADER = "customer_name,customer_id,transaction_time,amount,src_acc,dest_acc,transaction_type";

    public CliSearcher(ConfigLoader config, String type) {
        System.out.println(config.loadConfig("files.destination"));
        this.filePath = config.loadConfig("files.destination");
        if (type.equals("1")) {
        this.searcher = new FileSearcher(filePath);
        } else if (type.equals("2")) {
            this.searcher=new DbSearcher();
        }
    }

    public void startSearch() {
        int counter = 0;
        while (true) {
            System.out.println("enter a name");
            Scanner input = new Scanner(System.in);
            String keyword = input.nextLine();
            if (keyword.equals("q")) {
                return;
            } else {
                List<CustomerTransaction> transactions = searcher.search(keyword);
                if (transactions.size() > 0) {
                    CsvWriter<CustomerTransaction> writer = new CsvWriter<>("customerTransact" + counter + ".csv", filePath);
                    writer.writeToFile(HEADER, transactions);
                } else {
                    System.out.println("there is no transaction.customer doesnt exist.");
                }
            }
            counter++;
        }
    }
}
