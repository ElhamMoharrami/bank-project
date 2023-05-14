package com.elham.bankproject.searcher;

import com.elham.bankproject.common.ConfigLoader;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class MainS {
    public static void main(String[] args) {
        try {
            BasicConfigurator.configure();
            Logger log = Logger.getLogger(MainS.class.getName());
            ConfigLoader loadConfig = new ConfigLoader();
            loadConfig.setConfigLoc(args[0]);
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
                log.error("invalid entry!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("please enter a valid path to files directory");
        }
    }
}
