package com.elham.bankproject.searcher;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Scanner;

public class CliSearcher {
    private  Searcher searcher;
    private final String filePath;
    private final static String HEADER = "customer_name,customer_id,transaction_time,amount,src_acc,dest_acc,transaction_type";
    private static final Logger logger = LogManager.getLogger(CliSearcher.class);

    public CliSearcher(ConfigLoader config, String type) {
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
           logger.info("enter a name");
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
                    logger.warn("there is no transaction.customer doesnt exist.");
                }
            }
            counter++;
        }
    }
}
