package com.elham.bankproject.searcher;

import com.elham.bankproject.common.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Scanner;

public class MainSearcher {
    private static final Logger logger = LogManager.getLogger(MainSearcher.class.getName());

    public static void main(String[] args) {
        try {
            ConfigLoader loadConfig = new ConfigLoader();
            logger.info("Please choose method of searching:\n 1.search by files\n 2.search by database");
            Scanner input = new Scanner(System.in);
            String method = input.nextLine();
            if (method.equals("1")) {
                CliSearcher cliSearcher = new CliSearcher(loadConfig, "1");
                cliSearcher.startSearch();
            } else if (method.equals("2")) {
                CliSearcher cliSearcher = new CliSearcher(loadConfig, "2");
                cliSearcher.startSearch();
            } else {
               logger.warn("invalid entry!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
          logger.warn("please enter a valid path to files directory");
        }
    }
}
