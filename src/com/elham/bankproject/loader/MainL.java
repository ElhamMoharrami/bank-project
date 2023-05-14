package com.elham.bankproject.loader;

import com.elham.bankproject.common.ConfigLoader;

public class MainL {
    public static void main(String[] args) {
        try {
            ConfigLoader loadConfig = new ConfigLoader();
            loadConfig.setConfigLoc(args[0]);
            String fileLoc = loadConfig.loadConfig("files.destination");
            CreateTable createTable=new CreateTable();
            createTable.createTable();
            InsertToTable.insertAllData(fileLoc);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("please enter a valid path to config.properties");
        }
    }
}