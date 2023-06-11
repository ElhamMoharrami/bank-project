package com.elham.bankproject.searcher;

import com.elham.bankproject.common.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Scanner;

public class DataSearcher {
    private static final Logger logger = LogManager.getLogger(DataSearcher.class.getName());

    public static void main(String[] args) {
            ConfigLoader loadConfig = new ConfigLoader();
            System.out.println("Please choose method of searching:\n 1.search by files\n 2.search by database");
            Scanner input = new Scanner(System.in);
            String method = input.nextLine();
            if (method.equals("1")) {
                CliSearcher cliSearcher = new CliSearcher(loadConfig, "1");
                cliSearcher.startSearch();
            } else if (method.equals("2")) {
                CliSearcher cliSearcher = new CliSearcher(loadConfig, "2");
                cliSearcher.startSearch();
            } else {
                System.out.println("invalid entry!");
            }
    }
}
